package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.controllers.ChangePassword;
import system.gc.controllers.ControllerPermission;
import system.gc.dtos.EmployeeDTO;
import system.gc.dtos.TokenChangePasswordDTO;
import system.gc.dtos.TokenDTO;
import system.gc.exceptionsAdvice.exceptions.CodeChangePasswordInvalidException;
import system.gc.services.web.WebLogPasswordCode;
import system.gc.services.web.impl.WebEmployeeService;
import system.gc.utils.TextUtils;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@RestController
@RequestMapping(value = API_V1_WEB + "/employees")
@Slf4j
public class WebEmployeeController implements ControllerPermission, ChangePassword {
    @Autowired
    private WebEmployeeService webEmployeeService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WebLogPasswordCode webLogPasswordCodeEmployeeImpl;

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> listPaginationEmployees(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sort) {
        return ResponseEntity.ok(webEmployeeService.listPaginationEmployees(PageRequest.of(page, size, Sort.by(sort))));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Inserindo registro!");
        if (webEmployeeService.save(employeeDTO) == null) {
            return ResponseEntity.badRequest().body(messageSource.getMessage("TEXT_ERROR_INSERT_EMPLOYEE",
                    null,
                    LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_INSERT_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Atualizando registro");
        webEmployeeService.update(employeeDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<EmployeeDTO>> searchForCPF(HttpServletResponse response,
                                                          @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                          @RequestParam(name = "cpf") String cpf) {
        log.info("Localizando funcionário...");
        return ResponseEntity.ok(webEmployeeService.findByCPFPagination(PageRequest.of(page, size), new EmployeeDTO(cpf.trim())));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        webEmployeeService.delete(ID);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "list/to-modal-order-service")
    public ResponseEntity<List<EmployeeDTO>> findAllToModalOrderService() {
        log.info("Listando funcionários");
        return ResponseEntity.ok(webEmployeeService.findAllToModalOrderService());
    }

    @Override
    public ResponseEntity<String> requestCode(@RequestParam String email) {
        if (webLogPasswordCodeEmployeeImpl.generateCodeForChangePassword(email)) {
            return ResponseEntity.ok().body(messageSource.getMessage("TEXT_MSG_EMAIL_SENT_SUCCESS",
                    null, LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSource.getMessage("TEXT_ERROR_CHANGE_PASSWORD",
                null, LocaleContextHolder.getLocale()));
    }

    @Override
    public ResponseEntity<TokenDTO> receiveCode(String email, String code) {
        if (!(TextUtils.textIsValid(email) && TextUtils.textIsValid(code))) {
            throw new CodeChangePasswordInvalidException(messageSource.getMessage("TEXT_ERROR_EMAIL_EMPTY_OR_NULL",
                    null, LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok().body(webLogPasswordCodeEmployeeImpl.validateCode(email, code));
    }

    @Override
    public ResponseEntity<String> changePassword(TokenChangePasswordDTO tokenChangePasswordDTO) {
        webLogPasswordCodeEmployeeImpl.changePassword(tokenChangePasswordDTO.getToken(), tokenChangePasswordDTO.getNewPassword());
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_PASSWORD_UPDATE_SUCCESS",
                null, LocaleContextHolder.getLocale()));
    }
}