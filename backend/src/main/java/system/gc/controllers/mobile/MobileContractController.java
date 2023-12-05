package system.gc.controllers.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import system.gc.controllers.Pagination;
import system.gc.controllers.UserAuthenticatedController;
import system.gc.dtos.ContractDTO;
import system.gc.entities.Lessee;
import system.gc.exceptionsAdvice.exceptions.UserAuthenticatedException;
import system.gc.security.LesseeUserDetails;
import system.gc.services.mobile.MobileContractService;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@RestController
@RequestMapping(value = API_V1_MOBILE + "/contracts")
public class MobileContractController implements Pagination, UserAuthenticatedController {
    private final MobileContractService mobileContractService;

    private final MessageSource messageSource;


    @Autowired
    public MobileContractController(MobileContractService mobileContractService, MessageSource messageSource) {
        this.mobileContractService = mobileContractService;
        this.messageSource = messageSource;
    }

    @GetMapping(value = "lessee")
    public ResponseEntity<Page<ContractDTO>> lesseeContracts(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) throws UserAuthenticatedException {
        pageLimit(page);
        sizeLimit(size);
        Lessee lessee = getUserAuthenticated(LesseeUserDetails.class);
        return ResponseEntity.ok(mobileContractService.lesseeContracts(PageRequest.of(page, size), lessee.getId()));
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }
}
