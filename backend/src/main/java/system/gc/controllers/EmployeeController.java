package system.gc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.gc.dtos.EmployeeDTO;
import system.gc.services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("users")
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

}
