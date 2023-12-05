package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.services.web.impl.WebApplicationService;
import system.gc.services.web.impl.WebDataReloadService;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@RestController
@Slf4j
@RequestMapping(value = API_V1_WEB + "/data/reload")
public class WebDataController {

    @Autowired
    private WebDataReloadService webDataReloadService;

    @Autowired
    private WebApplicationService webApplicationService;

    @PutMapping
    public ResponseEntity<String> reload(@RequestParam(name = "insert", defaultValue = "INSERT_YES") String insert)
    {
        log.info("reload!");
        webDataReloadService.deleteAll();
        if (insert.equalsIgnoreCase("INSERT_YES"))
        {
            webApplicationService.insertAll();
        }
        return ResponseEntity.ok("Dados resetados com sucesso");
   }
}