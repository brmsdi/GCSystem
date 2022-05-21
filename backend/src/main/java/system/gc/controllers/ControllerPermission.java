package system.gc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface ControllerPermission {
    @GetMapping(value = "permission")
    default ResponseEntity<String> hasPermission()
    {
        return ResponseEntity.ok("ok");
    }
}
