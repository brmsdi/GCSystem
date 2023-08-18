package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import system.gc.dtos.RoleDTO;
import system.gc.services.web.impl.RoleService;

import java.util.List;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Controller
@RequestMapping(value = API_V1_WEB + "/roles")
@Slf4j
public class WebRoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll(@RequestParam(name = "sort", defaultValue = "name") String sort) {
        log.info("Listando funções");
        return ResponseEntity.ok(roleService.findAll(Sort.by(sort)));
    }
}
