package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<StatusDTO>> findAll() {
        log.info("Listando status");
        return ResponseEntity.ok(statusService.findAll());
    }
}