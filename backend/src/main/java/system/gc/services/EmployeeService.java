package system.gc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.dtos.EmployeeDTO;
import system.gc.entities.Employee;
import system.gc.entities.Role;
import system.gc.entities.Specialty;
import system.gc.entities.Status;
import system.gc.repositories.EmployeeRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * */
@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public EmployeeDTO save(EmployeeDTO newEmployeeDTO) {
        log.info("Salvando novo registro de funcionário no banco de dados. Nome: " + newEmployeeDTO.getName());
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Employee registeredEmployee = employeeRepository.save(employeeDTO.toEntity(newEmployeeDTO));
        if(registeredEmployee.getId() == null) {
            log.warn("Erro ao salvar!");
            return null;
        }
        log.info("Salvo com sucesso. ID: " + registeredEmployee.getId());
        return employeeDTO.toDTO(registeredEmployee);
    }

    public List<EmployeeDTO> findAll() {
        log.info("Listando funcionários");
        EmployeeDTO employeeDTO = new EmployeeDTO();
        List<EmployeeDTO> employeeDTOList = employeeDTO.convertListEntityFromListDTO(employeeRepository.findAll());
        return employeeDTOList.isEmpty() ? null : employeeDTOList;
    }
}
