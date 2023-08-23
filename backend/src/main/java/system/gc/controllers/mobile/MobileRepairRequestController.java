package system.gc.controllers.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import system.gc.controllers.ControllerPermission;
import system.gc.dtos.HttpMessageResponse;
import system.gc.dtos.ItemDTO;
import system.gc.entities.Employee;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;
import system.gc.security.EmployeeUserDetails;
import system.gc.services.mobile.MobileRepairRequestService;

import javax.validation.Valid;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

@RestController
@RequestMapping(value = API_V1_MOBILE + "/repair-requests")
public class MobileRepairRequestController implements ControllerPermission {

    @Autowired
    private MobileRepairRequestService mobileRepairRequestService;

    @Autowired
    private MessageSource messageSource;

    @PostMapping(value = "item")
    public ResponseEntity<ItemDTO> addItem(
            @RequestParam(name = "idRepairRequest") Integer idRepairRequest,
            @Valid @RequestBody ItemDTO itemDTO) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException {
        Employee employee = getUser();
        return ResponseEntity.ok(mobileRepairRequestService.addItem(employee.getId(), idRepairRequest, itemDTO));
    }

    @DeleteMapping(value = "item")
    public ResponseEntity<HttpMessageResponse> deleteItem(
            @RequestParam(name = "idRepairRequest") Integer idRepairRequest,
            @RequestParam(name = "idItem") Integer idItem) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException {
        Employee employee = getUser();
        mobileRepairRequestService.removeItem(employee.getId(), idRepairRequest, idItem);
        HttpMessageResponse httpMessageResponse = new HttpMessageResponse(HttpStatus.OK.toString(), messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(httpMessageResponse);
    }

    private Employee getUser() {
        return ((EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getUserAuthenticated();
    }
}
