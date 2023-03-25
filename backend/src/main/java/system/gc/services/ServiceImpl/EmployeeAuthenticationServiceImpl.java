package system.gc.services.ServiceImpl;

import org.springframework.stereotype.Service;
import system.gc.entities.Employee;
import system.gc.repositories.EmployeeRepository;
import system.gc.services.AuthenticationByCPFGeneric;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
public class EmployeeAuthenticationServiceImpl implements AuthenticationByCPFGeneric<Employee, EmployeeRepository> {}
