package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import system.gc.dtos.SpecialtyDTO;
import system.gc.services.ServiceImpl.SpecialtyService;

import java.util.List;

@Controller
@RequestMapping(value="/specialty")
@Slf4j
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> findAll() {
        log.info("Listando especialidades");
        return ResponseEntity.ok(specialtyService.findAll());
    }
}
