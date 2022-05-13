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
import system.gc.dtos.LesseeDTO;
import system.gc.dtos.OpenAndProgressAndLateRepairRequest;
import system.gc.dtos.RepairRequestDTO;
import system.gc.services.ServiceImpl.RepairRequestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/repair-requests")
@Slf4j
public class RepairRequestController {

    @Autowired
    private RepairRequestService repairRequestService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<Page<RepairRequestDTO>> listPaginationContract(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        log.info("Listando solicitações de reparo");
        return ResponseEntity.ok(repairRequestService.listPaginationRepairRequest(PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody RepairRequestDTO repairRequestDTO) {
        log.info("Inserindo nova solicitação");
        if (repairRequestService.save(repairRequestDTO) == null) {
            return ResponseEntity.ok(messageSource.getMessage("TEXT_ERROR_INSERT_REPAIR_REQUEST",
                    null,
                    LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_INSERT_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody RepairRequestDTO repairRequestDTO) {
        log.info("Atualizando registro");
        repairRequestService.update(repairRequestDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<RepairRequestDTO>> search(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                    @RequestParam(name = "cpf") String cpf) {
        log.info("Localizando solicitação de reparo");
        return ResponseEntity.ok(repairRequestService.searchRepairRequest(PageRequest.of(page, size), new LesseeDTO(cpf.trim())));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        repairRequestService.delete(ID);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "list/status-open-progress-late")
    public ResponseEntity<OpenAndProgressAndLateRepairRequest> perStatusRepairRequest() {
        return ResponseEntity.ok(repairRequestService.openAndProgressAndLateRepairRequest(List.of("Aberto", "Em andamento", "Atrasado")));
    }

    @GetMapping(value = "list/to-modal-order-service")
    public ResponseEntity<List<RepairRequestDTO>> findAllToModalOrderService() {
        log.info("Listando as solicitações de reparo");
        return ResponseEntity.ok(repairRequestService.findAllToModalOrderService(List.of("Aberto")));
    }

    @GetMapping(value = "list/order-service/to-modal-order-service")
    public ResponseEntity<List<RepairRequestDTO>> findAllPerOrderServiceAndStatus(@RequestParam(name = "id") Integer ID) {
        log.info("Listando as solicitações de reparo");
        return ResponseEntity.ok(repairRequestService.findAllPerOrderServiceAndStatus(ID, List.of("Aberto")));
    }
}
