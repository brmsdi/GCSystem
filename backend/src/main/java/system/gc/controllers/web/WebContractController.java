package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.dtos.ContractDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.services.web.impl.WebContractService;
import javax.validation.Valid;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@RestController
@RequestMapping(value = API_V1_WEB + "/contracts")
@Slf4j
public class WebContractController implements WebControllerPermission {

    @Autowired
    private WebContractService webContractService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<Page<ContractDTO>> listPaginationContract(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        log.info("Listando contratos");
        return ResponseEntity.ok(webContractService.listPaginationContract(PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody ContractDTO contractDTO) {
        if (webContractService.save(contractDTO) == null) {
            return ResponseEntity.ok(messageSource.getMessage("TEXT_ERROR_INSERT_CONTRACT",
                    null,
                    LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_INSERT_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody ContractDTO contractDTO) {
        log.info("Atualizando registro");
        webContractService.update(contractDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<ContractDTO>> search(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                    @RequestParam(name = "cpf") String cpf) {
        log.info("Localizando contratos...");
        return ResponseEntity.ok(webContractService.searchContract(PageRequest.of(page, size), new LesseeDTO(cpf.trim())));
    }

    @GetMapping(value = "printout")
    public ResponseEntity<ContractDTO> findByID(@RequestParam(name = "id", defaultValue = "0") Integer id) {
        return ResponseEntity.ok(webContractService.findByID(id));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        webContractService.delete(ID);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }
}
