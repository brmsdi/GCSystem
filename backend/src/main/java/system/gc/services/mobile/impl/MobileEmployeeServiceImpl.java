package system.gc.services.mobile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO myAccount(String username) throws EntityNotFoundException {
        Optional<Employee> employee = employeeRepository.findByCPF(username);
        employee.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        return new EmployeeDTO(employee.get());
    }
}
