package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import system.gc.dtos.StatusDTO;
import system.gc.services.ServiceImpl.StatusService;

import java.util.List;

@Controller
@RequestMapping(value = "/status")
@Slf4j
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<List<StatusDTO>> findAll(@RequestParam(name = "sort", defaultValue = "name") String sort) {
        log.info("Listando status");
        return ResponseEntity.ok(statusService.findAll(Sort.by(sort)));
    }

    @GetMapping(value = "condominium")
    public ResponseEntity<List<StatusDTO>> findAllFromViewCondominium() {
        log.info("Listando status");
        return ResponseEntity.ok(statusService.findAllFromView(List.of("Disponível", "Indisponível", "Lotado")));
    }


    @GetMapping(value = "contract")
    public ResponseEntity<List<StatusDTO>> findAllFromViewContract() {
        log.info("Listando status");
        return ResponseEntity.ok(statusService.findAllFromView(List.of("Ativo", "Encerrado", "Expirado", "Cancelado")));
    }

    @GetMapping(value = "debt")
    public ResponseEntity<List<StatusDTO>> findAllFromViewDebt() {
        log.info("Listando status");
        return ResponseEntity.ok(statusService.findAllFromView(List.of("Aberto", "Vencido", "Cancelado", "Pago")));
    }

    @GetMapping(value = "repair-request")
    public ResponseEntity<List<StatusDTO>> findAllFromViewRepairRequest() {
        log.info("Listando status");
        return ResponseEntity.ok(statusService.findAllFromView(List.of("Aberto", "Em andamento", "Concluído", "Atrasado")));
    }
}