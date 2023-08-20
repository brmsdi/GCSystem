package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.dtos.DebtDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.exceptionsAdvice.exceptions.DebtNotCreatedException;
import system.gc.services.web.impl.DebtService;

import javax.validation.Valid;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@RestController
@RequestMapping(value = API_V1_WEB + "/debts")
@Slf4j
public class WebDebtController implements WebControllerPermission {

    @Autowired
    private DebtService debtService;
    @GetMapping
    public ResponseEntity<Page<DebtDTO>> listPaginationDebt(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        log.info("Listando débitos");
        return ResponseEntity.ok(debtService.listPaginationDebts(PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<DebtDTO> save(@Valid @RequestBody DebtDTO debtDTO) throws DebtNotCreatedException {
        return ResponseEntity.ok(debtService.save(debtDTO));
    }

    @PutMapping
    public ResponseEntity<DebtDTO> update(@Valid @RequestBody DebtDTO debtDTO) {
        log.info("Atualizando registro");
        return ResponseEntity.ok(debtService.update(debtDTO));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<DebtDTO>> search(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                @RequestParam(name = "cpf") String cpf) {
        log.info("Localizando débitos");
        return ResponseEntity.ok(debtService.searchDebts(PageRequest.of(page, size), new LesseeDTO(cpf.trim())));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        return ResponseEntity.ok(debtService.delete(ID));
    }
}
