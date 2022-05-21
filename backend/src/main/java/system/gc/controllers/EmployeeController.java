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
import system.gc.dtos.EmployeeDTO;
import system.gc.services.ServiceImpl.EmployeeService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/employees")
@Slf4j
public class EmployeeController implements ControllerPermission {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> listPaginationEmployees(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sort) {
        return ResponseEntity.ok(employeeService.listPaginationEmployees(PageRequest.of(page, size, Sort.by(sort))));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Inserindo registro!");
        if (employeeService.save(employeeDTO) == null) {
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
        employeeService.update(employeeDTO);
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
        return ResponseEntity.ok(employeeService.findByCPFPagination(PageRequest.of(page, size), new EmployeeDTO(cpf.trim())));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        employeeService.delete(ID);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "list/to-modal-order-service")
    public ResponseEntity<List<EmployeeDTO>> findAllToModalOrderService() {
        log.info("Listando funcionários");
        return ResponseEntity.ok(employeeService.findAllToModalOrderService());
    }
}