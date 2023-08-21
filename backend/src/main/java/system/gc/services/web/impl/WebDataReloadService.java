package system.gc.services.web.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
public class WebDataReloadService {

    @Autowired
    private WebLogPasswordCodeService webLogPasswordCodeService;

    @Autowired
    private WebMovementService webMovementService;

    @Autowired
    private WebRepairRequestService webRepairRequestService;

    @Autowired
    private WebTypeProblemService webTypeProblemService;

    @Autowired
    private WebDebtService webDebtService;

    @Autowired
    private WebActivityTypeService webActivityTypeService;

    @Autowired
    private WebContractService webContractService;

    @Autowired
    private WebCondominiumService webCondominiumService;

    @Autowired
    private WebLocalizationCondominiumService webLocalizationCondominiumService;

    @Autowired
    private WebLocalizationService webLocalizationService;

    @Autowired
    private WebLesseeService webLesseeService;

    @Autowired
    private WebOrderServiceService webOrderServiceService;

    @Autowired
    private WebEmployeeService webEmployeeService;

    @Autowired
    private WebRoleService webRoleService;

    @Autowired
    private WebStatusService webStatusService;

    @Transactional
    public void deleteAll()
    {
        webLogPasswordCodeService.deleteAll();
        webMovementService.deleteAll();
        webRepairRequestService.deleteAll();
        webTypeProblemService.deleteAll();
        webDebtService.deleteAll();
        webActivityTypeService.deleteAll();
        webContractService.deleteAll();
        webCondominiumService.deleteAll();
        webLocalizationCondominiumService.deleteAll();
        webLocalizationService.deleteAll();
        webLesseeService.deleteAll();
        webOrderServiceService.deleteAll();
        webEmployeeService.deleteAll();
        webRoleService.deleteAll();
        webStatusService.deleteAll();
    }
}
