package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.dtos.EmployeeDTO;
import system.gc.services.EmployeeService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping(value="/users")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Inserindo registro!");

        if(!employeeService.findByCPF(employeeDTO).isEmpty()) {
            return ResponseEntity.ok(messageSource.getMessage("TEXT_ERROR_INSERT_CPF_DUPLICATED", null, LocaleContextHolder.getLocale()));
        }

        if(employeeService.save(employeeDTO) == null) {
            return ResponseEntity.ok(messageSource.getMessage("TEXT_ERROR_INSERT_EMPLOYEE",
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
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<EmployeeDTO>> searchForCPF(HttpServletResponse response, @RequestParam(name = "cpf") String cpf) throws IOException {
        log.info("Localizando funcionário...");
        EmployeeDTO employeeDTO = new EmployeeDTO(cpf);
        return ResponseEntity.ok(employeeService.findByCPF(employeeDTO));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeService.delete(employeeDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

}
