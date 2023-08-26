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
import system.gc.dtos.ItemDTO;
import system.gc.dtos.RepairRequestDTO;
import system.gc.dtos.ScreenNewRepairRequestMobileDataDTO;
import system.gc.entities.Employee;
import system.gc.entities.Lessee;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.UserAuthenticatedException;
import system.gc.security.EmployeeUserDetails;
import system.gc.security.LesseeUserDetails;
import system.gc.services.mobile.MobileRepairRequestService;

import javax.validation.Valid;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

@RestController
@RequestMapping(value = API_V1_MOBILE + "/repair-requests")
public class MobileRepairRequestController implements ControllerPermission, Pagination, UserAuthenticatedController {
    private final MobileRepairRequestService mobileRepairRequestService;
    private final MessageSource messageSource;

    @Autowired
    public MobileRepairRequestController(MobileRepairRequestService mobileRepairRequestService, MessageSource messageSource) {
        this.mobileRepairRequestService = mobileRepairRequestService;
        this.messageSource = messageSource;
    }

    @PostMapping(value = "lessee")
    public ResponseEntity<RepairRequestDTO> save(@Valid @RequestBody RepairRequestDTO repairRequestDTO) throws UserAuthenticatedException {
        Lessee lessee = getUserAuthenticated(LesseeUserDetails.class);
        return ResponseEntity.ok(mobileRepairRequestService.save(repairRequestDTO, lessee.getId()));
    }

    @GetMapping(value = "lessee")
    public ResponseEntity<Page<RepairRequestDTO>> findAllByEmployee(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) throws UserAuthenticatedException {
        pageLimit(page);
        sizeLimit(size);
        Lessee lessee = getUserAuthenticated(LesseeUserDetails.class);
        return ResponseEntity.ok(mobileRepairRequestService.lesseeRepairRequests(PageRequest.of(page, size), lessee.getId()));
    }

    @PostMapping(value = "item")
    public ResponseEntity<ItemDTO> addItem(
            @RequestParam(name = "idRepairRequest") Integer idRepairRequest,
            @Valid @RequestBody ItemDTO itemDTO) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException, UserAuthenticatedException {
        Employee employee = getUserAuthenticated(EmployeeUserDetails.class);
        return ResponseEntity.ok(mobileRepairRequestService.addItem(employee.getId(), idRepairRequest, itemDTO));
    }

    @DeleteMapping(value = "item")
    public ResponseEntity<HttpMessageResponse> deleteItem(
            @RequestParam(name = "idRepairRequest") Integer idRepairRequest,
            @RequestParam(name = "idItem") Integer idItem) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException, UserAuthenticatedException {
        Employee employee = getUserAuthenticated(EmployeeUserDetails.class);
        mobileRepairRequestService.removeItem(employee.getId(), idRepairRequest, idItem);
        HttpMessageResponse httpMessageResponse = new HttpMessageResponse(HttpStatus.OK.toString(), messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(httpMessageResponse);
    }

    @GetMapping(value = "lessee/search")
    public ResponseEntity<Page<RepairRequestDTO>> searchById(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "keySearch") Integer keySearch
    ) throws UserAuthenticatedException {
        pageLimit(page);
        sizeLimit(size);
        Lessee lessee = getUserAuthenticated(LesseeUserDetails.class);
        return ResponseEntity.ok(mobileRepairRequestService.searchById(PageRequest.of(page, size), lessee.getId(), keySearch));
    }

    @GetMapping(value = "screen-new-data")
    public ResponseEntity<ScreenNewRepairRequestMobileDataDTO> screenData() throws UserAuthenticatedException {
        Lessee lessee = getUserAuthenticated(LesseeUserDetails.class);
        return ResponseEntity.ok(mobileRepairRequestService.screenData(lessee.getId()));
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }
}