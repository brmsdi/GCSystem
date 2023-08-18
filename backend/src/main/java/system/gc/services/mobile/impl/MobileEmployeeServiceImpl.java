package system.gc.services.mobile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.EmployeeDTO;
import system.gc.entities.Employee;
import system.gc.repositories.EmployeeRepository;
import system.gc.services.mobile.MobileEmployeeService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MobileEmployeeServiceImpl implements MobileEmployeeService {

    private final EmployeeRepository employeeRepository;

    public MobileEmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO myAccount(String username) throws EntityNotFoundException {
        Optional<Employee> employee = employeeRepository.findByCPF(username);
        employee.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado" + username));
        return new EmployeeDTO(employee.get());
    }
}
