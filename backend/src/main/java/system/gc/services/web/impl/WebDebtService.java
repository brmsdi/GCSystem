package system.gc.services.web.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import system.gc.dtos.*;
import system.gc.entities.*;
import system.gc.exceptionsAdvice.exceptions.DebtNotCreatedException;
import system.gc.repositories.DebtRepository;
import system.gc.security.EmployeeUserDetails;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

import static system.gc.utils.TextUtils.*;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Service
@Slf4j
public class WebDebtService {
    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private WebLesseeService webLesseeService;

    @Autowired
    private WebMovementService webMovementService;

    @Autowired
    private WebActivityTypeService webActivityTypeService;

    @Autowired
    private WebStatusService webStatusService;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public DebtDTO save(DebtDTO debtDTO) throws DebtNotCreatedException {
        if (!webLesseeService.lesseeRegistrationIsEnabled(debtDTO.getLessee())) {
            log.warn("Locatário não está apto a receber débitos ou não existe no banco de dados");
            throw new DebtNotCreatedException(messageSource.getMessage("TEXT_ERROR_INSERT_DEBT",
                    null,
                    LocaleContextHolder.getLocale()));
        }
        Debt registeredDebt = debtRepository.save(new DebtDTO().toEntity(debtDTO));
        ActivityType activityTypeRegister = webActivityTypeService.findByName(ACTIVITY_TYPE_REGISTERED);
        log.info("Débito registrado com o id: " + registeredDebt.getId());
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        registerMovementDebt(registeredDebt, activityTypeRegister, employeeUserDetails.getUserAuthenticated());
        return new DebtDTO(registeredDebt);
    }

    @Transactional
    public Page<DebtDTO> listPaginationDebts(Pageable pageable) {
        log.info("Listando débitos");
        Page<Debt> page = debtRepository.findAll(pageable);
        if (!page.isEmpty()) {
            debtRepository.loadLazyDebts(page.toList());
        }
        return page.map(DebtDTO::new);
    }

    @Transactional
    public DebtDTO update(DebtDTO updateDebtDTO) throws EntityNotFoundException {
        log.info("Atualizando registro do débito");
        Optional<Debt> debt = debtRepository.findById(updateDebtDTO.getId());
        debt.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        DebtDTO previousDebt = new DebtDTO().toDTO(debt.get());
        Debt updatedDebt = debtRepository.save(new DebtDTO().toEntity(updateDebtDTO));
        ActivityType activityTypeUpdate = webActivityTypeService.findByName(ACTIVITY_TYPE_UPDATED);
        log.info("Atualizado com sucesso");
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        registerMovementDebt(previousDebt, updatedDebt, activityTypeUpdate, employeeUserDetails.getUserAuthenticated());
        return new DebtDTO(updatedDebt);
    }

    @Transactional
    public Page<DebtDTO> searchDebts(Pageable pageable, LesseeDTO lesseeDTO) {
        log.info("Buscando débitos");
        LesseeDTO lessee = webLesseeService.findByCPF(lesseeDTO);
        if(lessee == null) {
            log.warn("Locatário com o CPF: " + lesseeDTO.getCpf() + " não foi localizado");
            return Page.empty();
        }
        Page<Debt> pageDebts = debtRepository.findDebtsForLessee(pageable, lessee.getId());
        debtRepository.loadLazyDebts(pageDebts.toList());
        return pageDebts.map(DebtDTO::new);
    }

    @Transactional
    public String delete(Integer ID) throws EntityNotFoundException {
        log.info("Deletando registro com o ID: " + ID);
        Optional<Debt> debt = debtRepository.findById(ID);
        debt.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        DebtDTO previousDebt = new DebtDTO().toDTO(debt.get());
        Status statusDisable = webStatusService.findByName(STATUS_DELETED);
        ActivityType activityTypeDisable = webActivityTypeService.findByName(ACTIVITY_TYPE_DELETED);
        debt.get().setStatus(statusDisable);
        Debt disabledDebt = debtRepository.save(debt.get());
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        registerMovementDebt(previousDebt, disabledDebt, activityTypeDisable, employeeUserDetails.getUserAuthenticated());
        log.info("Registro desativado com sucesso");
        return messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale());
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        debtRepository.deleteAll();
    }

    private void registerMovementDebt(Debt debt, ActivityType activityType, Employee employee) {
        webMovementService.registerMovement(debt, activityType, employee);
    }

    private void registerMovementDebt(DebtDTO previousDebtDTO, Debt debt, ActivityType activityType, Employee employee) {
        webMovementService.registerMovement(previousDebtDTO, debt, activityType, employee);
    }
}
