package system.gc.services.ServiceImpl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import system.gc.dtos.DebtDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.entities.Lessee;
import system.gc.entities.PasswordCode;
import system.gc.entities.Status;
import system.gc.repositories.LesseeRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private PasswordCodeService passwordCodeService;

    @Autowired
    private GCEmailService gcEmailService;

    @Transactional
    public LesseeDTO save(LesseeDTO newLesseeDTO) {
        log.info("Salvando novo registro de locatário no banco de dados. Nome: " + newLesseeDTO.getName());
        LesseeDTO lesseeDTO = new LesseeDTO();
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
        LesseeDTO lesseeResultForCpf = findByCPF(updateLesseeDTO);
        if (lesseeResultForCpf != null && !Objects.equals(lessee.get().getId(), lesseeResultForCpf.getId())) {
            log.warn("Cpf não corresponde ao ID no banco de dados");
            throw new EntityNotFoundException("Cpf indisponível");
        }
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

    public LesseeDTO authentication(String username) {
        return lesseeAuthenticationServiceImpl.authentication(username, new LesseeDTO(), lesseeRepository);
    }

    @Transactional
    public boolean changePassword(String email) {
        log.info("Iniciando processo de geração de codigo para troca de senha");
        Status waitingStatus = statusService.findByName("Aguardando");
        Status cancelStatus = statusService.findByName("Cancelado");
        Lessee lesseeResult = lesseeAuthenticationServiceImpl.verifyEmail(email, lesseeRepository);
        Optional<Lessee> lesseeOptional = lesseeAuthenticationServiceImpl.CheckIfThereISAnOpenRequest(lesseeResult.getId(), lesseeRepository, waitingStatus.getId());
        if (lesseeOptional.isPresent()) {
            lesseeOptional.get().getPasswordCode().forEach(it -> it.setStatus(cancelStatus));
            passwordCodeService.updateStatusCode(lesseeOptional.get().getPasswordCode());
        }
        PasswordCode passwordCode = lesseeAuthenticationServiceImpl.startProcess(lesseeResult, statusService.findByName("Aguardando"), passwordCodeService);
        log.info("Enviando código para o E-mail");
        SimpleMailMessage simpleMailMessage = gcEmailService.createSimpleMessage(System.getenv("EMAIL_GCSYSTEM"), email, gcEmailService.getSubjectEmail(), passwordCode.getCode());
        gcEmailService.send(simpleMailMessage);
        return true;
    }
}
