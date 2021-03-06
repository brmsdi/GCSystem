package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import system.gc.dtos.*;
import system.gc.entities.*;
import system.gc.repositories.DebtRepository;
import system.gc.security.EmployeeUserDetails;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class DebtService {
    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private LesseeService lesseeService;

    @Autowired
    private MovementService movementService;

    @Autowired
    private ActivityTypeService activityTypeService;

    @Autowired
    private StatusService statusService;

    @Transactional
    public Debt save(DebtDTO debtDTO) {
        if (!lesseeService.lesseeRegistrationIsEnabled(debtDTO.getLessee())) {
            log.warn("Locatário não está apto a receber débitos ou não existe no banco de dados");
            return null;
        }
        Debt registeredDebt = debtRepository.save(new DebtDTO().toEntity(debtDTO));
        if (registeredDebt.getId() == null) {
            log.warn("Erro ao salvar!");
            return null;
        }
        ActivityType activityTypeRegister = activityTypeService.findByName("Registrado");
        log.info("Débito registrado com o id: " + registeredDebt.getId());
        // MODIFICAR
        // List<ActivityType> activitiesType =  new ActivityTypeDTO().convertListEntityDTOFromListEntity(activityTypeService.findAll());
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        registerMovementDebt(registeredDebt, activityTypeRegister, employeeUserDetails.getUserAuthenticated());
        return registeredDebt;
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
    public void update(DebtDTO updateDebtDTO) throws EntityNotFoundException {
        log.info("Atualizando registro do débito");
        Optional<Debt> debt = debtRepository.findById(updateDebtDTO.getId());
        debt.orElseThrow(() -> new EntityNotFoundException("Débito inexistente: "));
        ActivityType activityTypeUpdate = activityTypeService.findByName("Atualizado");
        DebtDTO previousDebt = new DebtDTO().toDTO(debt.get());
        Debt updatedDebt = debtRepository.save(new DebtDTO().toEntity(updateDebtDTO));
        log.info("Atualizado com sucesso");
        //List<ActivityType> activitiesType = new ActivityTypeDTO().convertListEntityDTOFromListEntity(activityTypeService.findAll());
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        registerMovementDebt(previousDebt, updatedDebt, activityTypeUpdate, employeeUserDetails.getUserAuthenticated());
    }

    @Transactional
    public Page<DebtDTO> searchDebts(Pageable pageable, LesseeDTO lesseeDTO) {
        log.info("Buscando débitos");
        LesseeDTO lessee = lesseeService.findByCPF(lesseeDTO);
        if(lessee == null) {
            log.warn("Locatário com o CPF: " + lesseeDTO.getCpf() + " não foi localizado");
            return Page.empty();
        }
        Page<Debt> pageDebts = debtRepository.findDebtsForLessee(pageable, lessee.getId());
        debtRepository.loadLazyDebts(pageDebts.toList());
        return pageDebts.map(DebtDTO::new);
    }

    @Transactional
    public void delete(Integer ID) throws EntityNotFoundException {
        log.info("Deletando registro com o ID: " + ID);
        Optional<Debt> debt = debtRepository.findById(ID);
        debt.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        DebtDTO previousDebt = new DebtDTO().toDTO(debt.get());
        Status statusDisable = statusService.findByName("Deletado");
        ActivityType activityTypeDisable = activityTypeService.findByName("Deletado");
        debt.get().setStatus(statusDisable);
        Debt disabledDebt = debtRepository.save(debt.get());
        //List<ActivityType> activitiesType = new ActivityTypeDTO().convertListEntityDTOFromListEntity(activityTypeService.findAll());
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        registerMovementDebt(previousDebt, disabledDebt, activityTypeDisable, employeeUserDetails.getUserAuthenticated());
        log.info("Registro desativado com sucesso");
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        debtRepository.deleteAll();
    }

    private void registerMovementDebt(Debt debt, ActivityType activityType, Employee employee) {
        movementService.registerMovement(debt, activityType, employee);
    }

    private void registerMovementDebt(DebtDTO previousDebtDTO, Debt debt, ActivityType activityType, Employee employee) {
        movementService.registerMovement(previousDebtDTO, debt, activityType, employee);
    }
}
