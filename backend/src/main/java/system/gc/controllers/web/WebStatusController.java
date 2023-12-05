package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import system.gc.dtos.StatusDTO;
import system.gc.services.web.impl.WebStatusService;

import java.util.List;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Controller
@RequestMapping(value = API_V1_WEB + "/status")
@Slf4j
public class WebStatusController {

    @Autowired
    private WebStatusService webStatusService;

    @GetMapping
    public ResponseEntity<List<StatusDTO>> findAll(@RequestParam(name = "sort", defaultValue = "name") String sort) {
        log.info("Listando status");
        return ResponseEntity.ok(webStatusService.findAll(Sort.by(sort)));
    }

    @GetMapping(value = "condominium")
    public ResponseEntity<List<StatusDTO>> findAllToViewCondominium() {
        log.info("Listando status");
        return ResponseEntity.ok(webStatusService.findAllToViewDTO(List.of("Disponível", "Indisponível", "Lotado")));
    }


    @GetMapping(value = "contract")
    public ResponseEntity<List<StatusDTO>> findAllToViewContract() {
        log.info("Listando status");
        return ResponseEntity.ok(webStatusService.findAllToViewDTO(List.of("Ativo", "Encerrado", "Expirado", "Cancelado")));
    }

    @GetMapping(value = "debt")
    public ResponseEntity<List<StatusDTO>> findAllToViewDebt() {
        log.info("Listando status");
        return ResponseEntity.ok(webStatusService.findAllToViewDTO(List.of("Aberto", "Vencido", "Cancelado", "Pago")));
    }

    @GetMapping(value = "repair-request")
    public ResponseEntity<List<StatusDTO>> findAllToViewRepairRequest() {
        log.info("Listando status");
        return ResponseEntity.ok(webStatusService.findAllToViewDTO(List.of("Aberto", "Cancelado", "Concluído", "Em andamento")));
    }

    @GetMapping(value = "order-service")
    public ResponseEntity<List<StatusDTO>> findAllToViewOrderService() {
        log.info("Listando status");
        return ResponseEntity.ok(webStatusService.findAllToViewDTO(List.of("Aberto", "Cancelado", "Concluído")));
    }
}