package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.services.ServiceImpl.ApplicationService;
import system.gc.services.ServiceImpl.DataReloadService;
/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@RestController
@Slf4j
@RequestMapping(value = "/data/reload")
public class DataController {

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