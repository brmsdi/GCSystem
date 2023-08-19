package system.gc.controllers.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import system.gc.dtos.OrderServiceDTO;
import system.gc.entities.Employee;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderService;
import system.gc.security.EmployeeUserDetails;
import system.gc.services.mobile.MobileOrderServiceService;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

@RestController
@RequestMapping(value = API_V1_MOBILE + "/order-services")
public class MobileOrderServiceController {

    @Autowired
    private MobileOrderServiceService mobileOrderServiceService;

    @GetMapping(value = "employee")
    public Page<OrderServiceDTO> findAllByEmployee(
            @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Employee employee = getUser();
        return mobileOrderServiceService.employeeOrders(PageRequest.of(page, 5), employee.getId());
    }

    @GetMapping(value = "details")
    public ResponseEntity<OrderServiceDTO> detailsOrderService(
            @RequestParam(name = "id") Integer id) throws AccessDeniedOrderService {
        Employee employee = getUser();
        return ResponseEntity.ok(mobileOrderServiceService.detailsOrderService(id, employee.getId()));
    }

    private Employee getUser() {
        return ((EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getUserAuthenticated();
    }
}
