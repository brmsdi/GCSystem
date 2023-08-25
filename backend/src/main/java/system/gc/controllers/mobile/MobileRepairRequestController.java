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
import system.gc.controllers.Pagination;
import system.gc.dtos.HttpMessageResponse;
import system.gc.dtos.ItemDTO;
import system.gc.dtos.RepairRequestDTO;
import system.gc.entities.Employee;
import system.gc.entities.Lessee;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;
import system.gc.security.EmployeeUserDetails;
import system.gc.security.LesseeUserDetails;
import system.gc.services.mobile.MobileRepairRequestService;

import javax.validation.Valid;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

@RestController
@RequestMapping(value = API_V1_MOBILE + "/repair-requests")
public class MobileRepairRequestController implements ControllerPermission, Pagination {
    private final MobileRepairRequestService mobileRepairRequestService;
    private final MessageSource messageSource;

    @Autowired
    public MobileRepairRequestController(MobileRepairRequestService mobileRepairRequestService, MessageSource messageSource) {
        this.mobileRepairRequestService = mobileRepairRequestService;
        this.messageSource = messageSource;
    }

    @GetMapping(value = "lessee")
    public ResponseEntity<Page<RepairRequestDTO>> findALlByEmployee(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Lessee lessee = getLessee();
        pageLimit(page);
        sizeLimit(size);
        return ResponseEntity.ok(mobileRepairRequestService.lesseeRepairRequests(PageRequest.of(page, size), lessee.getId()));
    }

    @PostMapping(value = "item")
    public ResponseEntity<ItemDTO> addItem(
            @RequestParam(name = "idRepairRequest") Integer idRepairRequest,
            @Valid @RequestBody ItemDTO itemDTO) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException {
        Employee employee = getEmployee();
        return ResponseEntity.ok(mobileRepairRequestService.addItem(employee.getId(), idRepairRequest, itemDTO));
    }

    @DeleteMapping(value = "item")
    public ResponseEntity<HttpMessageResponse> deleteItem(
            @RequestParam(name = "idRepairRequest") Integer idRepairRequest,
            @RequestParam(name = "idItem") Integer idItem) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException {
        Employee employee = getEmployee();
        mobileRepairRequestService.removeItem(employee.getId(), idRepairRequest, idItem);
        HttpMessageResponse httpMessageResponse = new HttpMessageResponse(HttpStatus.OK.toString(), messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(httpMessageResponse);
    }

    private Employee getEmployee() {
        return ((EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getUserAuthenticated();
    }

    private Lessee getLessee() {
        return ((LesseeUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getUserAuthenticated();
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }
}