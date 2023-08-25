package system.gc.controllers.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.controllers.ControllerPermission;
import system.gc.controllers.Pagination;
import system.gc.controllers.UserAuthenticatedController;
import system.gc.dtos.HttpMessageResponse;
import system.gc.dtos.OrderServiceDTO;
import system.gc.entities.Employee;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.UserAuthenticatedException;
import system.gc.security.EmployeeUserDetails;
import system.gc.services.mobile.MobileOrderServiceService;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

@RestController
@RequestMapping(value = API_V1_MOBILE + "/order-services")
public class MobileOrderServiceController implements ControllerPermission, Pagination, UserAuthenticatedController {
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
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) throws UserAuthenticatedException {
        Employee employee = getUserAuthenticated(EmployeeUserDetails.class);
        pageLimit(page);
        sizeLimit(size);
        return mobileOrderServiceService.employeeOrders(PageRequest.of(page, size), employee.getId());
    }

    @GetMapping(value = "details")
    public ResponseEntity<OrderServiceDTO> detailsOrderService(
            @RequestParam(name = "id") Integer id) throws AccessDeniedOrderServiceException, UserAuthenticatedException {
        Employee employee = getUserAuthenticated(EmployeeUserDetails.class);
        return ResponseEntity.ok(mobileOrderServiceService.detailsOrderService(id, employee.getId()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<OrderServiceDTO>> findByIdFromEmployee(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "idOrderService") Integer idOrderService
    ) throws IllegalArgumentException, UserAuthenticatedException {
        Employee employee = getUserAuthenticated(EmployeeUserDetails.class);
        pageLimit(page);
        sizeLimit(size);
        return ResponseEntity.ok(mobileOrderServiceService.findByIdFromEmployee(PageRequest.of(page, size), employee.getId(), idOrderService));
    }

    @PostMapping(value = "order-service/close")
    public ResponseEntity<HttpMessageResponse> close(@RequestParam(name = "id") Integer id) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException, UserAuthenticatedException {
        Employee employee = getUserAuthenticated(EmployeeUserDetails.class);
        mobileOrderServiceService.closeOrderService(employee.getId(), id);
        return ResponseEntity.ok(new HttpMessageResponse(HttpStatus.OK.toString(), messageSource.getMessage("TEXT_MSG_CLOSE_ORDER_SUCCESS", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }
}