package system.gc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.gc.configuration.exceptions.CodeChangePasswordInvalidException;
import system.gc.dtos.TokenDTO;
import system.gc.services.ServiceImpl.EmployeeService;
import system.gc.services.ServiceImpl.LesseeService;
import system.gc.services.ServiceImpl.PasswordCodeService;
import system.gc.utils.TextUtils;
import system.gc.utils.TypeUserEnum;

@RestController
@RequestMapping(value = "/changePassword")
public class AuthenticationController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LesseeService lesseeService;

    @Autowired
    private PasswordCodeService passwordCodeService;

    @PostMapping(value = "requestCode")
    public ResponseEntity<String> requestCode(String email, Integer type) {
        if (!(TextUtils.textIsValid(email) && type != null)) {
            return ResponseEntity.badRequest().body(messageSource.getMessage("TEXT_ERROR_EMAIL_EMPTY_OR_NULL",
                    null, LocaleContextHolder.getLocale()));
        }

        if (TypeUserEnum.valueOf(type) == TypeUserEnum.EMPLOYEE) {
            //EmployeeDTO employeeDTO = employeeChangePasswordService.verifyEmail(email);
            if (employeeService.changePassword(email)) {
                return ResponseEntity.ok().body(messageSource.getMessage("TEXT_MSG_EMAIL_SENT_SUCCESS",
                        null, LocaleContextHolder.getLocale()));
            }
        } else if (TypeUserEnum.valueOf(type) == TypeUserEnum.LESSEE) {
            //LesseeDTO lesseeDTO = lesseeChangePasswordService.verifyEmail(email);
            if (lesseeService.changePassword(email)) {
                return ResponseEntity.ok().body(messageSource.getMessage("TEXT_MSG_EMAIL_SENT_SUCCESS",
                        null, LocaleContextHolder.getLocale()));
            }
        }

        return ResponseEntity.ok().body(messageSource.getMessage("TEXT_ERROR_CHANGE_PASSWORD",
                null, LocaleContextHolder.getLocale()));
    }

    @PostMapping(value = "receiveCode")
    public ResponseEntity<TokenDTO> receiveCode(String email, Integer type, String code) {
        if (!(TextUtils.textIsValid(email) && type != null && TextUtils.textIsValid(code))) {
            throw new CodeChangePasswordInvalidException(messageSource.getMessage("TEXT_ERROR_EMAIL_EMPTY_OR_NULL",
                    null, LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok().body(passwordCodeService.validateCode(email, type, code));
    }
}
