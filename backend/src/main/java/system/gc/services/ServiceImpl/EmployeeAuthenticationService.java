package system.gc.services.ServiceImpl;

import org.springframework.stereotype.Service;
import system.gc.dtos.EmployeeDTO;
import system.gc.entities.Employee;
import system.gc.repositories.EmployeeRepository;
import system.gc.services.AuthenticationByCPFGeneric;
import system.gc.services.ChangePasswordInterface;

@Service
public class EmployeeAuthenticationService implements AuthenticationByCPFGeneric<EmployeeDTO, Employee, EmployeeRepository>, ChangePasswordInterface<Employee, EmployeeRepository> {
}
