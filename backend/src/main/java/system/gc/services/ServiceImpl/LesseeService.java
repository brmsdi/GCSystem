package system.gc.services.ServiceImpl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.gc.configuration.exceptions.CodeChangePasswordInvalidException;
import system.gc.configuration.exceptions.DuplicatedFieldException;
import system.gc.dtos.DebtDTO;
import system.gc.dtos.EmployeeDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.entities.Employee;
import system.gc.entities.Lessee;
import system.gc.entities.LogChangePassword;
import system.gc.entities.Status;
import system.gc.repositories.LesseeRepository;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class LesseeService {
    @Autowired
    private LesseeRepository lesseeRepository;

    @Autowired
    private LesseeAuthenticationService lesseeAuthenticationServiceImpl;

    @Autowired
    private StatusService statusService;

    @Autowired
    private LogPasswordCodeService logPasswordCodeService;

    @Autowired
    private GCEmailService gcEmailService;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public LesseeDTO save(LesseeDTO newLesseeDTO) {
        log.info("Salvando novo registro de locatário no banco de dados. Nome: " + newLesseeDTO.getName());
        LesseeDTO lesseeDTO = new LesseeDTO();
        newLesseeDTO.setPassword(new BCryptPasswordEncoder().encode(newLesseeDTO.getPassword()));
        cpfIsAvailableSave(newLesseeDTO);
        emailIsAvailableSave(newLesseeDTO);
        Lessee registeredLessee = lesseeRepository.save(lesseeDTO.toEntity(newLesseeDTO));
        if (registeredLessee.getId() == null) {
            log.warn("Erro ao salvar!");
            return null;
        }
        log.info("Salvo com sucesso. ID: " + registeredLessee.getId());
        return lesseeDTO.toDTO(registeredLessee);
    }

    @Transactional
    public Page<LesseeDTO> listPaginationLessees(Pageable pageable) {
        log.info("Listando locatários");
        Page<Lessee> page = lesseeRepository.findAll(pageable);
        if (!page.isEmpty()) {
            lesseeRepository.loadLazyLessees(page.toList());
        }
        return page.map(LesseeDTO::new);
    }

    @Transactional
    public void update(LesseeDTO updateLesseeDTO) throws EntityNotFoundException {
        log.info("Atualizando registro do locatário");
        Optional<Lessee> lessee = lesseeRepository.findById(updateLesseeDTO.getId());
        lessee.orElseThrow(() -> new EntityNotFoundException("Não existe registro com o id: " + updateLesseeDTO.getId()));
        cpfIsAvailableUpdate(updateLesseeDTO);
        emailIsAvailableUpdate(updateLesseeDTO);
        updateLesseeDTO.setPassword(lessee.get().getPassword());
        lesseeRepository.save(new LesseeDTO().toEntity(updateLesseeDTO));
        log.info("Atualizado com sucesso");
    }

    @Transactional
    public LesseeDTO findByCPF(LesseeDTO lesseeDTO) {
        log.info("Localizando registro do locatário com o cpf: " + lesseeDTO.getCpf());
        Optional<Lessee> lessee = lesseeRepository.findByCPF(lesseeDTO.getCpf());
        if (lessee.isEmpty()) {
            log.warn("Registro com o cpf: " + lesseeDTO.getCpf() + " não foi localizado");
            return null;
        }
        lesseeRepository.loadLazyLessees(lessee.stream().toList());
        log.info("Registro com o CPF:  " + lesseeDTO.getCpf() + " localizado");
        return new LesseeDTO().toDTO(lessee.get());
    }

    @Transactional
    public Page<LesseeDTO> findByCPFPagination(Pageable pageable, LesseeDTO lesseeDTO) {
        log.info("Localizando registro do locatário com o cpf: " + lesseeDTO.getCpf());
        Page<Lessee> page = lesseeRepository.findByCPF(pageable, lesseeDTO.getCpf());
        if (page.isEmpty()) {
            log.warn("Registro com o cpf " + lesseeDTO.getCpf() + " não foi localizado");
            return Page.empty();
        }
        lesseeRepository.loadLazyLessees(page.toList());
        log.info("Registro do locatário com o cpf: " + lesseeDTO.getCpf() + " localizado com sucesso");
        return page.map(LesseeDTO::new);
    }

    @Transactional
    public void delete(Integer ID) throws EntityNotFoundException {
        log.info("Deletando registro com o ID: " + ID);
        Optional<Lessee> lessee = lesseeRepository.findById(ID);
        lessee.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        lesseeRepository.delete(lessee.get());
        log.info("Registro deletado com sucesso");
    }

    @Transactional
    public Page<LesseeDTO> listPaginationDebtsByLessee(LesseeDTO lesseeDTO, Page<DebtDTO> debtDTOPage) {
        for (DebtDTO debtDTO : debtDTOPage) {
            lesseeDTO.getDebts().add(DebtDTO.toViewByLessee(debtDTO));
        }
        return new PageImpl<>(List.of(lesseeDTO), debtDTOPage.getPageable(), debtDTOPage.getTotalElements());
    }

    public boolean isEnabled(@NonNull LesseeDTO lesseeDTO) {
        return lesseeDTO.getStatus().getName().equalsIgnoreCase("Ativo");
    }

    public boolean lesseeRegistrationIsEnabled(LesseeDTO lesseeDTO) {
        return findByCPF(lesseeDTO) != null && isEnabled(lesseeDTO);
    }

    public Lessee authentication(String username) {
        return lesseeAuthenticationServiceImpl.authentication(username, lesseeRepository);
    }

    @Transactional
    public boolean generateCodeForChangePassword(String email) {
        log.info("Iniciando processo de geração de codigo para troca de senha");
        Status waitingStatus = statusService.findByName("Aguardando");
        Status cancelStatus = statusService.findByName("Cancelado");
        Lessee lesseeResult = lesseeAuthenticationServiceImpl.verifyEmail(email, lesseeRepository);
        Optional<Lessee> lesseeOptional = lesseeAuthenticationServiceImpl.CheckIfThereISAnOpenRequest(lesseeResult.getId(), lesseeRepository, waitingStatus.getId());
        if (lesseeOptional.isPresent()) {
            lesseeOptional.get().getLogChangePassword().forEach(it -> it.setStatus(cancelStatus));
            logPasswordCodeService.updateStatusCode(lesseeOptional.get().getLogChangePassword());
        }
        LogChangePassword logChangePassword = lesseeAuthenticationServiceImpl.startProcess(lesseeResult, statusService.findByName("Aguardando"), logPasswordCodeService);
        log.info("Enviando código para o E-mail");
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("code", logChangePassword.getCode());
        MimeMessage mimeMessage = gcEmailService.createMimeMessage(System.getenv("EMAIL_GCSYSTEM"), email, gcEmailService.getSubjectEmail(), bodyParams);
        gcEmailService.send(mimeMessage);
        log.info("Código enviado para o E-mail");
        return true;
    }

    @Transactional
    public void changePassword(String token, String newPassword) {
        log.info("Atualizando senha");
        Status statusValid = statusService.findByName("Valido");
        Status statusRescued = statusService.findByName("Resgatado");
        Optional<Lessee> lesseeOptional = lesseeAuthenticationServiceImpl.verifyTokenForChangePassword(token, lesseeRepository, statusValid.getId());
        Lessee lessee = lesseeOptional.orElseThrow(() -> new CodeChangePasswordInvalidException("Nenhum registro encontrado para a solicitação"));
        lessee.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        lesseeRepository.save(lessee);
        lessee.getLogChangePassword().forEach(it -> it.setStatus(statusRescued));
        logPasswordCodeService.updateStatusCode(lessee.getLogChangePassword());
    }


    public void cpfIsAvailableSave(LesseeDTO lesseeDTO) {
        log.info("Verificando CPF");
        Optional<Lessee> lesseeOptional = lesseeRepository.findByCPF(lesseeDTO.getCpf());
        if (lesseeOptional.isPresent()) {
            log.warn("O CPF não está disponível");
            throw new DuplicatedFieldException(messageSource.getMessage("TEXT_ERROR_INSERT_CPF_DUPLICATED", null, LocaleContextHolder.getLocale()));
        }
    }
    public void cpfIsAvailableUpdate(LesseeDTO lesseeDTO)  {
        log.info("Verificando CPF");
        Optional<Lessee> lesseeOptional = lesseeRepository.findByCPF(lesseeDTO.getCpf());
        if (lesseeOptional.isPresent() && !lesseeDTO.getId().equals(lesseeOptional.get().getId())) {
            log.warn("O CPF não está disponível");
            throw new DuplicatedFieldException(messageSource.getMessage("TEXT_ERROR_INSERT_CPF_DUPLICATED", null, LocaleContextHolder.getLocale()));
        }
    }

    public void emailIsAvailableSave(LesseeDTO lesseeDTO) {
        log.info("Verificando E-mail");
        Optional<Lessee> lesseeOptional = lesseeRepository.findByEMAIL(lesseeDTO.getEmail());
        if (lesseeOptional.isPresent()) {
            log.warn("O E-MAIL não está disponível");
            throw new DuplicatedFieldException(messageSource.getMessage("TEXT_ERROR_INSERT_EMAIL_DUPLICATED", null, LocaleContextHolder.getLocale()));
        }
    }

    public void emailIsAvailableUpdate(LesseeDTO lesseeDTO) {
        log.info("Verificando E-mail");
        Optional<Lessee> lesseeOptional = lesseeRepository.findByEMAIL(lesseeDTO.getEmail());
        if (lesseeOptional.isPresent() && !lesseeDTO.getId().equals(lesseeOptional.get().getId())) {
            log.warn("O E-MAIL não está disponível");
            throw new DuplicatedFieldException(messageSource.getMessage("TEXT_ERROR_INSERT_EMAIL_DUPLICATED", null, LocaleContextHolder.getLocale()));
        }
    }
}
