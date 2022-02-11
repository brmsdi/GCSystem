package system.gc.services.ServiceImpl;

import org.springframework.stereotype.Service;
import system.gc.dtos.EmployeeDTO;
import system.gc.entities.Employee;
import system.gc.repositories.EmployeeRepository;
import system.gc.services.AuthenticationByCPFGenericImpl;

@Service
public class AuthenticationEmployee implements AuthenticationByCPFGenericImpl<EmployeeDTO, Employee, EmployeeRepository> {

}
