package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.dtos.DebtDTO;
import system.gc.services.ServiceImpl.DebtService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/debts")
@Slf4j
public class DebtController {

    @Autowired
    private DebtService debtService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<Page<DebtDTO>> listPaginationDebt(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        log.info("Listando débitos");
        return ResponseEntity.ok(debtService.listPaginationDebts(PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody DebtDTO debtDTO) {
        if (debtService.save(debtDTO) == null) {
            return ResponseEntity.ok(messageSource.getMessage("TEXT_ERROR_INSERT_DEBT",
                    null,
                    LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_INSERT_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody DebtDTO debtDTO) {
        log.info("Atualizando registro");
        debtService.update(debtDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        debtService.delete(ID);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

}
