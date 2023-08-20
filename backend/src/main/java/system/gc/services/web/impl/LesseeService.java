package system.gc.services.web.impl;

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
import system.gc.exceptionsAdvice.exceptions.DuplicatedFieldException;
import system.gc.controllers.web.WebControllerPermission;
import system.gc.dtos.DebtDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.dtos.StatusDTO;
import system.gc.entities.Lessee;
import system.gc.entities.Status;
import system.gc.repositories.LesseeRepository;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

import static system.gc.utils.TextUtils.STATUS_ACTIVE;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
@Slf4j
public class LesseeService implements WebControllerPermission {
    @Autowired
    private LesseeRepository lesseeRepository;

    @Autowired
    private LesseeAuthenticationService lesseeAuthenticationServiceImpl;

    @Autowired
    private StatusService statusService;

    @Autowired
    private LogPasswordCodeService logPasswordCodeService;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public LesseeDTO save(LesseeDTO newLesseeDTO) {
        log.info("Salvando novo registro de locatário no banco de dados. Nome: " + newLesseeDTO.getName());
        LesseeDTO lesseeDTO = new LesseeDTO();
        newLesseeDTO.setPassword(new BCryptPasswordEncoder().encode(newLesseeDTO.getPassword()));
        cpfIsAvailableSave(newLesseeDTO);
        emailIsAvailableSave(newLesseeDTO);
        Status statusActive = statusService.findByName(STATUS_ACTIVE);
        newLesseeDTO.setStatus(new StatusDTO().toDTO(statusActive));
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
        lessee.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
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
        lessee.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        lesseeRepository.delete(lessee.get());
        log.info("Registro deletado com sucesso");
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        lesseeRepository.deleteAll();
    }

    @Transactional
    public Page<LesseeDTO> listPaginationDebtsByLessee(LesseeDTO lesseeDTO, Page<DebtDTO> debtDTOPage) {
        for (DebtDTO debtDTO : debtDTOPage) {
            lesseeDTO.getDebts().add(DebtDTO.toViewByLessee(debtDTO));
        }
        return new PageImpl<>(List.of(lesseeDTO), debtDTOPage.getPageable(), debtDTOPage.getTotalElements());
    }

    public boolean isEnabled(@NonNull LesseeDTO lesseeDTO) {
        return lesseeDTO.getStatus().getName().equalsIgnoreCase(STATUS_ACTIVE);
    }

    public boolean lesseeRegistrationIsEnabled(LesseeDTO lesseeDTO) {
        return findByCPF(lesseeDTO) != null && isEnabled(lesseeDTO);
    }

    public Lessee authentication(String username) {
        return lesseeAuthenticationServiceImpl.authentication(username, lesseeRepository);
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