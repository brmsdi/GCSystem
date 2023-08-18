package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.services.web.impl.ApplicationService;
import system.gc.services.web.impl.DataReloadService;

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
    private DataReloadService dataReloadService;

    @Autowired
    private ApplicationService applicationService;

    @PutMapping
    public ResponseEntity<String> reload(@RequestParam(name = "insert", defaultValue = "INSERT_YES") String insert)
    {
        log.info("reload!");
        dataReloadService.deleteAll();
        if (insert.equalsIgnoreCase("INSERT_YES"))
        {
            applicationService.insertAll();
        }
        return ResponseEntity.ok("Dados resetados com sucesso");
   }
}