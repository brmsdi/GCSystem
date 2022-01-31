package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import system.gc.dtos.RoleDTO;
import system.gc.services.RoleService;

import java.util.List;

@Controller
@RequestMapping(value="/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() {
        log.info("Listando funções");
        return ResponseEntity.ok(roleService.findAll());
    }
}
