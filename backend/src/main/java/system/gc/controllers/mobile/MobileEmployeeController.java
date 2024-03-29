package system.gc.controllers.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import system.gc.controllers.ControllerPermission;
import system.gc.controllers.MobileChangePassword;
import system.gc.dtos.EmployeeDTO;
import system.gc.dtos.HttpMessageResponse;
import system.gc.dtos.TokenChangePasswordDTO;
import system.gc.dtos.TokenDTO;
import system.gc.exceptionsAdvice.exceptions.CodeChangePasswordInvalidException;
import system.gc.security.UserDetailsConvert;
import system.gc.services.mobile.MobileEmployeeService;
import system.gc.services.web.WebLogPasswordCode;
import system.gc.utils.TextUtils;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

@RestController
@RequestMapping(value = API_V1_MOBILE + "/employees")
public class MobileEmployeeController implements ControllerPermission, MobileChangePassword {
    private final MobileEmployeeService mobileEmployeeService;
    private final MessageSource messageSource;
    private final WebLogPasswordCode webLogPasswordCode;

    @Autowired
    public MobileEmployeeController(MobileEmployeeService mobileEmployeeService,
                                    MessageSource messageSource,
                                    @Qualifier("WebLogPasswordCodeEmployee") WebLogPasswordCode webLogPasswordCode) {
        this.mobileEmployeeService = mobileEmployeeService;
        this.messageSource = messageSource;
        this.webLogPasswordCode = webLogPasswordCode;
    }

    @GetMapping(value = "account")
    public ResponseEntity<EmployeeDTO> myAccount() {
        UserDetailsConvert userDetailsConvert = (UserDetailsConvert) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return ResponseEntity.ok(mobileEmployeeService.myAccount(userDetailsConvert.getUsername()));
    }

    public ResponseEntity<HttpMessageResponse> requestCode(@RequestParam String email) {
        if (webLogPasswordCode.generateCodeForChangePassword(email)) {
            return ResponseEntity.ok().body(new HttpMessageResponse(HttpStatus.OK.toString(), messageSource.getMessage("TEXT_MSG_EMAIL_SENT_SUCCESS",
                    null, LocaleContextHolder.getLocale())));
        }
        return ResponseEntity.ok().body(new HttpMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), messageSource.getMessage("TEXT_ERROR_CHANGE_PASSWORD",
                null, LocaleContextHolder.getLocale())));
    }

    @Override
    public ResponseEntity<TokenDTO> receiveCode(String email, String code) {
        if (!(TextUtils.textIsValid(email) && TextUtils.textIsValid(code))) {
            throw new CodeChangePasswordInvalidException(messageSource.getMessage("TEXT_ERROR_EMAIL_EMPTY_OR_NULL",
                    null, LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok().body(webLogPasswordCode.validateCode(email, code));
    }

    @Override
    public ResponseEntity<HttpMessageResponse> changePassword(TokenChangePasswordDTO tokenChangePasswordDTO) {
        webLogPasswordCode.changePassword(tokenChangePasswordDTO.getToken(), tokenChangePasswordDTO.getNewPassword());
        return ResponseEntity.ok(new HttpMessageResponse(HttpStatus.OK.toString(), messageSource.getMessage("TEXT_MSG_PASSWORD_UPDATE_SUCCESS",
                null, LocaleContextHolder.getLocale())));
    }
}