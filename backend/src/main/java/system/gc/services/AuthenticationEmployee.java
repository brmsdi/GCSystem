package system.gc.services;

import org.springframework.stereotype.Service;
import system.gc.dtos.EmployeeDTO;
import system.gc.entities.Employee;
import system.gc.repositories.EmployeeRepository;

@Service
public class AuthenticationEmployee implements AuthenticationByCPFGenericImpl<EmployeeDTO, Employee, EmployeeRepository> {

}
