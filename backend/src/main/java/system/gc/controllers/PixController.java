package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.configuration.GenerateTXIDImpl;
import system.gc.entities.payment.Charge;
import system.gc.entities.payment.PaginationPixDueCharge;
import system.gc.entities.payment.QRCode;
import system.gc.services.ServiceImpl.PixService;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping(value = "v1/pix")
@Slf4j
public class PixController {

    @Autowired
    private PixService pixService;

    @PostMapping("new")
    public ResponseEntity<Charge> createChargePixWithExpirationDate(@RequestBody Charge charge) throws Exception {
        log.info("criando nova cobrança pix");
        return ResponseEntity.ok(pixService.pixCreateDueCharge(new JSONObject(charge), new GenerateTXIDImpl()));
    }

    @GetMapping("list")
    public ResponseEntity<PaginationPixDueCharge> listChargesPixByCPF(HttpServletRequest request) throws Exception {
        log.info("Listando cobranças pix");
        HashMap<String, String> params = new HashMap<>();
        request
                .getParameterMap()
                .forEach((key, value) -> params.put (key, value[0]));
        return ResponseEntity.ok(pixService.pixListDueCharges(params, new JSONObject()));
    }

    @GetMapping("details")
    public ResponseEntity<Charge> details(@RequestParam(name = "txid") String txid) throws Exception {
        log.info("Buscando detalhes da cobrança pix");
        HashMap<String, String> params = new HashMap<>();
        params.put("txid", txid);
        return ResponseEntity.ok(pixService.pixDetailsDueCharge(params, new JSONObject()));
    }

    @GetMapping("qrcode")
    public ResponseEntity<QRCode> generateQRCode(@RequestParam(name = "id") String id) throws Exception {
        log.info("Gerando QRCODE");
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        return ResponseEntity.ok(pixService.generateQRCode(params, new JSONObject()));
    }

    @PostMapping("update")
    public ResponseEntity<Charge> updateCharge(@RequestParam String txid, @RequestBody String charge) throws Exception {
        log.info("Atualizando cobrança pix");
        HashMap<String, String> params = new HashMap<>();
        params.put("txid", txid);
        return ResponseEntity.ok(pixService.updateChargePix(params, new JSONObject(charge)));
    }
}