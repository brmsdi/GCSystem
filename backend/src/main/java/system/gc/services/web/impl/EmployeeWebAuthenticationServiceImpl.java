package system.gc.services.web.impl;

import org.springframework.stereotype.Service;
import system.gc.entities.Employee;
import system.gc.repositories.EmployeeRepository;
import system.gc.services.web.WebAuthenticationByCPFGeneric;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
public class EmployeeWebAuthenticationServiceImpl implements WebAuthenticationByCPFGeneric<Employee, EmployeeRepository> {}
