package system.gc.controllers.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import system.gc.controllers.Pagination;
import system.gc.controllers.UserAuthenticatedController;
import system.gc.dtos.ContractDocumentDTO;
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

@Controller
@RequestMapping(value = API_V1_MOBILE + "/contract-view")
public class MobileContractViewController implements Pagination, UserAuthenticatedController {
    private final MobileContractService mobileContractService;

    private final MessageSource messageSource;

    @Autowired
    public MobileContractViewController(MobileContractService mobileContractService, MessageSource messageSource) {
        this.mobileContractService = mobileContractService;
        this.messageSource = messageSource;
    }

    @GetMapping(value = "printout-contract")
    public String printout(
            @RequestParam(name = "id") Integer id,
            Model model
    ) throws UserAuthenticatedException {
        Lessee lessee = getUserAuthenticated(LesseeUserDetails.class);
        ContractDocumentDTO contractDocumentDTO = mobileContractService.findByIdForLessee(lessee.getId(), id);
        model.addAttribute("contract", contractDocumentDTO);
        return "contract";
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }
}