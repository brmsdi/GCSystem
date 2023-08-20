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
import system.gc.dtos.LesseeDTO;
import system.gc.dtos.TokenChangePasswordDTO;
import system.gc.dtos.TokenDTO;
import system.gc.exceptionsAdvice.exceptions.CodeChangePasswordInvalidException;
import system.gc.services.web.LogPasswordCode;
import system.gc.services.web.impl.DebtService;
import system.gc.services.web.impl.LesseeService;
import system.gc.utils.TextUtils;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@RestController
@RequestMapping(value = API_V1_WEB + "/lessees")
@Slf4j
public class WebLesseeController implements WebControllerPermission, WebChangePassword {
    @Autowired
    private LesseeService lesseeService;

    @Autowired
    private DebtService debtService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LogPasswordCode logPasswordCodeLesseeImpl;

    @GetMapping
    public ResponseEntity<Page<LesseeDTO>> listPaginationLessees(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sort) {
        return ResponseEntity.ok(lesseeService.listPaginationLessees(PageRequest.of(page, size, Sort.by(sort))));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody LesseeDTO lesseeDTO) {
        log.info("Inserindo registro!");
        if (lesseeService.save(lesseeDTO) == null) {
            return ResponseEntity.badRequest().body(messageSource.getMessage("TEXT_ERROR_INSERT_LESSEE",
                    null,
                    LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_INSERT_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody LesseeDTO lesseeDTO) {
        log.info("Atualizando registro");
        lesseeService.update(lesseeDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<LesseeDTO>> searchForCPF(HttpServletResponse response,
                                                        @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                        @RequestParam(name = "cpf") String cpf) {
        log.info("Localizando locatário...");
        return ResponseEntity.ok(lesseeService.findByCPFPagination(PageRequest.of(page, size), new LesseeDTO(cpf.trim())));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        lesseeService.delete(ID);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "debt")
    public ResponseEntity<Page<LesseeDTO>> listPaginationDebtsByLessee(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                       @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                                       @RequestParam(name = "cpf") String cpf) {

        log.info("Localizando débito");
        LesseeDTO lessee = lesseeService.findByCPF(new LesseeDTO(cpf.trim()));

        if (lessee == null) {
            log.warn("Locatário com o CPF: " + cpf + " não foi localizado");
            return ResponseEntity.ok(Page.empty());
        }
        return ResponseEntity.ok(lesseeService.listPaginationDebtsByLessee(lessee, debtService.searchDebts(PageRequest.of(page, size), lessee)));
    }

    @Override
    public ResponseEntity<String> requestCode(@RequestParam String email) {
        if (logPasswordCodeLesseeImpl.generateCodeForChangePassword(email)) {
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
        return ResponseEntity.ok().body(logPasswordCodeLesseeImpl.validateCode(email, code));
    }

    @Override
    public ResponseEntity<String> changePassword(TokenChangePasswordDTO tokenChangePasswordDTO) {
        logPasswordCodeLesseeImpl.changePassword(tokenChangePasswordDTO.getToken(), tokenChangePasswordDTO.getNewPassword());
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_PASSWORD_UPDATE_SUCCESS",
                null, LocaleContextHolder.getLocale()));
    }
}
