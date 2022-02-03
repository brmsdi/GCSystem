package system.gc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.dtos.EmployeeDTO;
import system.gc.entities.Employee;
import system.gc.repositories.EmployeeRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Page<EmployeeDTO> findAllPageable(Pageable pageable) {
        log.info("Listando funcionários");
        Page<Employee> page = employeeRepository.findAll(pageable);
        employeeRepository.findEmployeesPagination(page.toList());
        return page.map(EmployeeDTO::new);
    }

    @Transactional
    public void update(EmployeeDTO updateEmployeeDTO) throws EntityNotFoundException {
        log.info("Atualizando registro do funionário");
        Optional<Employee> employee = employeeRepository.findByCPF(updateEmployeeDTO.getCpf());
        employee.orElseThrow(() -> new EntityNotFoundException("Não existe registro com o CPF: " + updateEmployeeDTO.getCpf()));
        updateEmployeeDTO.setId(employee.get().getId());
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeRepository.save(employeeDTO.toEntity(updateEmployeeDTO));
        log.info("Atualizado com sucesso");
    }

    public Page<EmployeeDTO> findByCPF(EmployeeDTO employeeDTO) {
        log.info("Localizando registro do funcionário com o cpf: " + employeeDTO.getCpf());
        Page<Employee> page = employeeRepository.findByCPF(PageRequest.of(0, 5), employeeDTO.getCpf());
        if(page.isEmpty()) {
            log.warn("Registro com o cpf " + employeeDTO.getCpf() + " não foi localizado");
            return page.map(EmployeeDTO::new);
        }
        employeeRepository.findEmployeesPagination(page.stream().toList());
        log.info("Registro do funcionário com o cpf: " + employeeDTO.getCpf() + " localizado com sucesso");
        return page.map(EmployeeDTO::new);
    }

    @Transactional
    public void delete(EmployeeDTO employeeDTO) throws EntityNotFoundException{
        log.info("Deletando registro com o CPF: " + employeeDTO.getCpf());
        Optional<Employee> employee = employeeRepository.findByCPF(employeeDTO.getCpf());
        employee.orElseThrow(() -> new EntityNotFoundException("Não existe registro com o CPF: " + employeeDTO.getCpf()));
        employeeRepository.delete(employee.get());
        log.info("Registro deletado com sucesso");
    }
}
