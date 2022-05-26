package system.gc.services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DataReloadService {

    @Autowired
    private LogPasswordCodeService logPasswordCodeService;

    @Autowired
    private MovementService movementService;

    @Autowired
    private RepairRequestService repairRequestService;

    @Autowired
    private TypeProblemService typeProblemService;

    @Autowired
    private DebtService debtService;

    @Autowired
    private ActivityTypeService activityTypeService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private CondominiumService condominiumService;

    @Autowired
    private LocalizationService localizationService;

    @Autowired
    private LesseeService lesseeService;

    @Autowired
    private OrderServiceService orderServiceService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StatusService statusService;

    @Transactional
    public void deleteAll()
    {
        logPasswordCodeService.deleteAll();
        movementService.deleteAll();
        repairRequestService.deleteAll();
        typeProblemService.deleteAll();
        debtService.deleteAll();
        activityTypeService.deleteAll();
        contractService.deleteAll();
        condominiumService.deleteAll();
        localizationService.deleteAll();
        lesseeService.deleteAll();
        orderServiceService.deleteAll();
        employeeService.deleteAll();
        roleService.deleteAll();
        statusService.deleteAll();
    }
}
