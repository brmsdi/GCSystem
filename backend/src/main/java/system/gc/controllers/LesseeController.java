package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.dtos.LesseeDTO;
import system.gc.services.ServiceImpl.DebtService;
import system.gc.services.ServiceImpl.LesseeService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/lessees")
@Slf4j
public class LesseeController implements ControllerPermission {
    @Autowired
    private LesseeService lesseeService;

    @Autowired
    private DebtService debtService;

    @Autowired
    private MessageSource messageSource;

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
        log.info("Localizando locat??rio...");
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

        log.info("Localizando d??bito");
        LesseeDTO lessee = lesseeService.findByCPF(new LesseeDTO(cpf.trim()));

        if (lessee == null) {
            log.warn("Locat??rio com o CPF: " + cpf + " n??o foi localizado");
            return ResponseEntity.ok(Page.empty());
        }
        return ResponseEntity.ok(lesseeService.listPaginationDebtsByLessee(lessee, debtService.searchDebts(PageRequest.of(page, size), lessee)));
    }
}
