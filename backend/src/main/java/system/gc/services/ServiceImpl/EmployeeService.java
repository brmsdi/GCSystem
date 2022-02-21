package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.gc.configuration.exceptions.CodeChangePasswordInvalidException;
import system.gc.dtos.EmployeeDTO;
import system.gc.entities.Employee;
import system.gc.entities.LogChangePassword;
import system.gc.entities.Status;
import system.gc.repositories.EmployeeRepository;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Wisley Bruno Marques França
 */
@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeAuthenticationService employeeAuthenticationServiceImpl;

    @Autowired
    private StatusService statusService;

    @Autowired
    private LogPasswordCodeService logPasswordCodeService;

    @Autowired
    private GCEmailService gcEmailService;

    @Transactional
    public EmployeeDTO save(EmployeeDTO newEmployeeDTO) {
        log.info("Salvando novo registro de funcionário no banco de dados. Nome: " + newEmployeeDTO.getName());
        EmployeeDTO employeeDTO = new EmployeeDTO();
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
    public Employee findByID(Integer ID) {
        return employeeRepository.findById(ID).orElse(null);
    }

    @Transactional
    public void update(EmployeeDTO updateEmployeeDTO) throws EntityNotFoundException {
        log.info("Atualizando registro do funionário");
        Optional<Employee> employee = employeeRepository.findById(updateEmployeeDTO.getId());
        employee.orElseThrow(() -> new EntityNotFoundException("Não existe registro com o id: " + updateEmployeeDTO.getId()));
        //updateEmployeeDTO.setId(employee.get().getId());
        EmployeeDTO employeeResultForCpf = findByCPF(updateEmployeeDTO);
        if (employeeResultForCpf != null && !Objects.equals(employee.get().getId(), employeeResultForCpf.getId())) {
            log.warn("Cpf não corresponde ao ID no banco de dados");
            throw new EntityNotFoundException("Cpf indisponível");
        }
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
        employee.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        employeeRepository.delete(employee.get());
        log.info("Registro deletado com sucesso");
    }

    public EmployeeDTO authentication(String username) {
        return employeeAuthenticationServiceImpl.authentication(username, new EmployeeDTO(), employeeRepository);
    }

    @Transactional
    public boolean generateCodeForChangePassword(String email) {
        log.info("Iniciando processo de geração de codigo para troca de senha");
        Status waitingStatus = statusService.findByName("Aguardando");
        Status cancelStatus = statusService.findByName("Cancelado");
        Employee employeeResult = employeeAuthenticationServiceImpl.verifyEmail(email, employeeRepository);
        Optional<Employee> employeeOptional = employeeAuthenticationServiceImpl.CheckIfThereISAnOpenRequest(employeeResult.getId(), employeeRepository, waitingStatus.getId());
        if (employeeOptional.isPresent()) {
            employeeOptional.get().getLogChangePassword().forEach(it -> it.setStatus(cancelStatus));
            logPasswordCodeService.updateStatusCode(employeeOptional.get().getLogChangePassword());
        }
        LogChangePassword logChangePassword = employeeAuthenticationServiceImpl.startProcess(employeeResult, waitingStatus, logPasswordCodeService);
        log.info("Enviando código para o E-mail");
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("code", logChangePassword.getCode());
        MimeMessage mimeMessage = gcEmailService.createMimeMessage(System.getenv("EMAIL_GCSYSTEM"), email, gcEmailService.getSubjectEmail(), bodyParams);
        gcEmailService.send(mimeMessage);
        log.info("Código enviado para o E-mail");
        return true;
    }

    @Transactional
    public void changePassword(String token, String newPassword) {
        log.info("Atualizando senha");
        Status statusValid = statusService.findByName("Valido");
        Status statusRescued = statusService.findByName("Resgatado");
        Optional<Employee> employeeOptional = employeeAuthenticationServiceImpl.verifyTokenForChangePassword(token, employeeRepository, statusValid.getId());
        Employee employee = employeeOptional.orElseThrow(() -> new CodeChangePasswordInvalidException("Nenhum registro encontrado para a solicitação"));
        employee.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        employeeRepository.save(employee);
        employee.getLogChangePassword().forEach(it -> it.setStatus(statusRescued));
        logPasswordCodeService.updateStatusCode(employee.getLogChangePassword());
    }
}
