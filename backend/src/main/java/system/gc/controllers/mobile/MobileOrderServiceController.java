package system.gc.controllers.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import system.gc.controllers.ControllerPermission;
import system.gc.dtos.HttpMessageResponse;
import system.gc.dtos.OrderServiceDTO;
import system.gc.entities.Employee;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;
import system.gc.security.EmployeeUserDetails;
import system.gc.services.mobile.MobileOrderServiceService;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

@RestController
@RequestMapping(value = API_V1_MOBILE + "/order-services")
public class MobileOrderServiceController implements ControllerPermission{
    private final MobileOrderServiceService mobileOrderServiceService;
    private final MessageSource messageSource;

    @Autowired
    public MobileOrderServiceController(MobileOrderServiceService mobileOrderServiceService,
                                        MessageSource messageSource) {
        this.mobileOrderServiceService = mobileOrderServiceService;
        this.messageSource = messageSource;
    }

    @GetMapping(value = "employee")
    public Page<OrderServiceDTO> findAllByEmployee(
            @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Employee employee = getUser();
        return mobileOrderServiceService.employeeOrders(PageRequest.of(page, 5), employee.getId());
    }

    @GetMapping(value = "details")
    public ResponseEntity<OrderServiceDTO> detailsOrderService(
            @RequestParam(name = "id") Integer id) throws AccessDeniedOrderServiceException {
        Employee employee = getUser();
        return ResponseEntity.ok(mobileOrderServiceService.detailsOrderService(id, employee.getId()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<OrderServiceDTO>> findByIdFromEmployee(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "idOrderService") Integer idOrderService
    ) {
        Employee employee = getUser();
        if (page < 0) page = 0;
        return ResponseEntity.ok(mobileOrderServiceService.findByIdFromEmployee(PageRequest.of(page, 5), employee.getId(), idOrderService));
    }

    @PostMapping(value = "order-service/close")
    public ResponseEntity<HttpMessageResponse> close(@RequestParam(name = "id") Integer id) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException {
        Employee employee = getUser();
        mobileOrderServiceService.closeOrderService(employee.getId(), id);
        return ResponseEntity.ok(new HttpMessageResponse(HttpStatus.OK.toString(), messageSource.getMessage("TEXT_MSG_CLOSE_ORDER_SUCCESS", null, LocaleContextHolder.getLocale())));
    }

    private Employee getUser() {
        return ((EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getUserAuthenticated();
    }
}