package system.gc.services.ServiceImpl;

import org.springframework.stereotype.Service;
import system.gc.entities.Employee;
import system.gc.repositories.EmployeeRepository;
import system.gc.services.AuthenticationByCPFGeneric;
import system.gc.services.ChangePasswordInterface;

/**
 * @author Wisley Bruno Marques França
 */
@Service
public class EmployeeAuthenticationServiceImpl implements AuthenticationByCPFGeneric<Employee, EmployeeRepository>, ChangePasswordInterface<Employee, EmployeeRepository> {}
