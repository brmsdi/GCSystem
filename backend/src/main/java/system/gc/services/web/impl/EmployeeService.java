package system.gc.services.web.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.gc.exceptionsAdvice.exceptions.DuplicatedFieldException;
import system.gc.dtos.EmployeeDTO;
import system.gc.dtos.StatusDTO;
import system.gc.entities.Employee;
import system.gc.entities.Status;
import system.gc.repositories.EmployeeRepository;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

import static system.gc.utils.TextUtils.STATUS_ACTIVE;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeAuthenticationServiceImpl employeeAuthenticationServiceImpl;

    @Autowired
    private StatusService statusService;

    @Autowired
    private LogPasswordCodeService logPasswordCodeService;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public EmployeeDTO save(EmployeeDTO newEmployeeDTO) {
        log.info("Salvando novo registro de funcionário no banco de dados. Nome: " + newEmployeeDTO.getName());
        EmployeeDTO employeeDTO = new EmployeeDTO();
        newEmployeeDTO.setPassword(new BCryptPasswordEncoder().encode(newEmployeeDTO.getPassword()));
        cpfIsAvailableSave(newEmployeeDTO);
        emailIsAvailableSave(newEmployeeDTO);
        Status statusActive = statusService.findByName(STATUS_ACTIVE);
        newEmployeeDTO.setStatus(new StatusDTO().toDTO(statusActive));
        Employee registeredEmployee = employeeRepository.save(employeeDTO.toEntity(newEmployeeDTO));
        if (registeredEmployee.getId() == null) {
            log.warn("Erro ao salvar!");
            return null;
        }
        log.info("Salvo com sucesso. ID: " + registeredEmployee.getId());
        return employeeDTO.toDTO(registeredEmployee);
    }

    @Transactional
    public Page<EmployeeDTO> listPaginationEmployees(Pageable pageable) {
        log.info("Listando funcionários");
        Page<Employee> page = employeeRepository.findAll(pageable);
        if (!page.isEmpty()) {
            employeeRepository.loadLazyEmployees(page.toList());
        }
        return page.map(EmployeeDTO::new);
    }

    @Transactional
    public void update(EmployeeDTO updateEmployeeDTO) throws EntityNotFoundException {
        log.info("Atualizando registro do funcionário");
        Optional<Employee> employee = employeeRepository.findById(updateEmployeeDTO.getId());
        employee.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        cpfIsAvailableUpdate(updateEmployeeDTO);
        emailIsAvailableUpdate(updateEmployeeDTO);
        updateEmployeeDTO.setPassword(employee.get().getPassword());
        employeeRepository.save(new EmployeeDTO().toEntity(updateEmployeeDTO));
        log.info("Atualizado com sucesso");
    }

    @Transactional
    public EmployeeDTO findByCPF(EmployeeDTO employeeDTO) {
        log.info("Localizando registro do funcionário com o cpf: " + employeeDTO.getCpf());
        Optional<Employee> employee = employeeRepository.findByCPF(employeeDTO.getCpf());
        if (employee.isEmpty()) {
            log.warn("Registro com o cpf: " + employeeDTO.getCpf() + " não foi localizado");
            return null;
        }
        employeeRepository.loadLazyEmployees(List.of(employee.get()));
        log.info("Registro com o CPF:  " + employeeDTO.getCpf() + " localizado");
        return new EmployeeDTO().toDTO(employee.get());
    }

    @Transactional
    public Page<EmployeeDTO> findByCPFPagination(Pageable pageable, EmployeeDTO employeeDTO) {
        log.info("Localizando registro do funcionário com o cpf: " + employeeDTO.getCpf());
        Page<Employee> page = employeeRepository.findByCPF(pageable, employeeDTO.getCpf());
        if (page.isEmpty()) {
            log.warn("Registro com o cpf " + employeeDTO.getCpf() + " não foi localizado");
            return Page.empty();
        }
        employeeRepository.loadLazyEmployees(page.toList());
        log.info("Registro do funcionário com o cpf: " + employeeDTO.getCpf() + " localizado com sucesso");
        return page.map(EmployeeDTO::new);
    }

    @Transactional
    public void delete(Integer ID) throws EntityNotFoundException {
        log.info("Deletando registro com o ID: " + ID);
        Optional<Employee> employee = employeeRepository.findById(ID);
        employee.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        employeeRepository.delete(employee.get());
        log.info("Registro deletado com sucesso");
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        employeeRepository.deleteAll();
    }

    public Employee authentication(String username) {
        return employeeAuthenticationServiceImpl.authentication(username, employeeRepository);
    }
    public void cpfIsAvailableSave(EmployeeDTO employeeDTO) {
        log.info("Verificando CPF");
        Optional<Employee> employeeOptional = employeeRepository.findByCPF(employeeDTO.getCpf());
        if (employeeOptional.isPresent()) {
            log.warn("O CPF não está disponível");
            throw new DuplicatedFieldException(messageSource.getMessage("TEXT_ERROR_INSERT_CPF_DUPLICATED", null, LocaleContextHolder.getLocale()));
        }
    }

    public void cpfIsAvailableUpdate(EmployeeDTO employeeDTO)  {
        log.info("Verificando CPF");
        Optional<Employee> employeeOptional = employeeRepository.findByCPF(employeeDTO.getCpf());
        if (employeeOptional.isPresent() && !employeeDTO.getId().equals(employeeOptional.get().getId())) {
            log.warn("O CPF não está disponível");
            throw new DuplicatedFieldException(messageSource.getMessage("TEXT_ERROR_INSERT_CPF_DUPLICATED", null, LocaleContextHolder.getLocale()));
        }
    }

    public void emailIsAvailableSave(EmployeeDTO employeeDTO) {
        log.info("Verificando E-mail");
        Optional<Employee> employeeOptional = employeeRepository.findByEMAIL(employeeDTO.getEmail());
        if (employeeOptional.isPresent()) {
            log.warn("O E-MAIL não está disponível");
            throw new DuplicatedFieldException(messageSource.getMessage("TEXT_ERROR_INSERT_EMAIL_DUPLICATED", null, LocaleContextHolder.getLocale()));
        }
    }

    public void emailIsAvailableUpdate(EmployeeDTO employeeDTO) {
        log.info("Verificando E-mail");
        Optional<Employee> employeeOptional = employeeRepository.findByEMAIL(employeeDTO.getEmail());
        if (employeeOptional.isPresent() && !employeeDTO.getId().equals(employeeOptional.get().getId())) {
            log.warn("O E-MAIL não está disponível");
            throw new DuplicatedFieldException(messageSource.getMessage("TEXT_ERROR_INSERT_EMAIL_DUPLICATED", null, LocaleContextHolder.getLocale()));
        }
    }

    public List<EmployeeDTO> findAllToModalOrderService() {
        List<Employee> employeeList = employeeRepository.findAll();
        employeeRepository.loadLazyEmployees(employeeList);
        return employeeList.stream().map(EmployeeDTO::new).toList();
    }
}