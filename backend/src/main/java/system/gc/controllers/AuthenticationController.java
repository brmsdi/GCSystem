package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import system.gc.exceptionsAdvice.exceptions.CodeChangePasswordInvalidException;
import system.gc.dtos.TokenChangePasswordDTO;
import system.gc.dtos.TokenDTO;
import system.gc.security.UserAuthenticatedView;
import system.gc.security.UserDetailsConvert;
import system.gc.services.ServiceImpl.EmployeeService;
import system.gc.services.ServiceImpl.LesseeService;
import system.gc.services.ServiceImpl.LogPasswordCodeService;
import system.gc.utils.TextUtils;
import system.gc.utils.TypeUserEnum;

@RestController
@Slf4j
@RequestMapping(value = "/change-password")
public class AuthenticationController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LesseeService lesseeService;

    @Autowired
    private LogPasswordCodeService logPasswordCodeService;

    @PostMapping(value = "request-code")
    public ResponseEntity<String> requestCode(String email, Integer type) {
        if (!(TextUtils.textIsValid(email) && type != null)) {
            return ResponseEntity.badRequest().body(messageSource.getMessage("TEXT_ERROR_EMAIL_EMPTY_OR_NULL",
                    null, LocaleContextHolder.getLocale()));
        }

        if (TypeUserEnum.valueOf(type) == TypeUserEnum.EMPLOYEE) {
            //EmployeeDTO employeeDTO = employeeChangePasswordService.verifyEmail(email);
            if (employeeService.generateCodeForChangePassword(email)) {
                return ResponseEntity.ok().body(messageSource.getMessage("TEXT_MSG_EMAIL_SENT_SUCCESS",
                        null, LocaleContextHolder.getLocale()));
            }
        } else if (TypeUserEnum.valueOf(type) == TypeUserEnum.LESSEE) {
            if (lesseeService.generateCodeForChangePassword(email)) {
                return ResponseEntity.ok().body(messageSource.getMessage("TEXT_MSG_EMAIL_SENT_SUCCESS",
                        null, LocaleContextHolder.getLocale()));
            }
        }
        return ResponseEntity.ok().body(messageSource.getMessage("TEXT_ERROR_CHANGE_PASSWORD",
                null, LocaleContextHolder.getLocale()));
    }

    @PostMapping(value = "receive-code")
    public ResponseEntity<TokenDTO> receiveCode(String email, Integer type, String code) {
        if (!(TextUtils.textIsValid(email) && type != null && TextUtils.textIsValid(code))) {
            throw new CodeChangePasswordInvalidException(messageSource.getMessage("TEXT_ERROR_EMAIL_EMPTY_OR_NULL",
                    null, LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok().body(logPasswordCodeService.validateCode(email, type, code));
    }

    @PostMapping(value = "change")
    public ResponseEntity<String> changePassword(@RequestBody TokenChangePasswordDTO tokenChangePasswordDTO) {
        if (tokenChangePasswordDTO.getType().equalsIgnoreCase(String.valueOf(TypeUserEnum.EMPLOYEE))) {
            employeeService.changePassword(tokenChangePasswordDTO.getToken(), tokenChangePasswordDTO.getNewPassword());
            return ResponseEntity.ok( messageSource.getMessage("TEXT_MSG_PASSWORD_UPDATE_SUCCESS",
                    null, LocaleContextHolder.getLocale()) );
        } else if (tokenChangePasswordDTO.getType().equalsIgnoreCase(String.valueOf(TypeUserEnum.LESSEE))) {
            lesseeService.changePassword(tokenChangePasswordDTO.getToken(), tokenChangePasswordDTO.getNewPassword());
            return ResponseEntity.ok( messageSource.getMessage("TEXT_MSG_PASSWORD_UPDATE_SUCCESS",
                    null, LocaleContextHolder.getLocale()) );
        }

        throw new IllegalArgumentException("Entrada invalida!");
    }

    @GetMapping("/validate/token")
    public ResponseEntity<UserAuthenticatedView> validateToken() {
        log.info("Token valido");
        UserDetailsConvert user = (UserDetailsConvert) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return ResponseEntity.ok(user.getNameAndRoleUser());
    }
}
