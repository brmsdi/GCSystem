package system.gc.services.web.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.TokenDTO;
import system.gc.entities.Employee;
import system.gc.entities.LogChangePassword;
import system.gc.entities.Status;
import system.gc.exceptionsAdvice.exceptions.CodeChangePasswordInvalidException;
import system.gc.repositories.EmployeeRepository;
import system.gc.services.web.WebLogPasswordCode;
import system.gc.utils.TypeUserEnum;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static system.gc.utils.TextUtils.*;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
@Log4j2
@Service("WebLogPasswordCodeEmployee")
public class WebLogPasswordCodeEmployeeImpl implements WebLogPasswordCode<Employee, EmployeeRepository> {

    @Autowired
    private GCEmailService gcEmailService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WebLogPasswordCodeService webLogPasswordCodeService;

    @Autowired
    private WebStatusService webStatusService;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public boolean generateCodeForChangePassword(String email)
    {
        log.info("Iniciando processo de geração de codigo para troca de senha");
        Status waitingStatus = webStatusService.findByName(STATUS_WAITING);
        Status cancelStatus = webStatusService.findByName(STATUS_CANCELED);
        Employee employeeResult = verifyEmail(email, employeeRepository);
        Optional<Employee> employeeOptional = checkIfThereISAnOpenRequest(employeeResult.getId(), employeeRepository, waitingStatus.getId());
        if (employeeOptional.isPresent()) {
            employeeOptional.get().getLogChangePassword().forEach(it -> it.setStatus(cancelStatus));
            webLogPasswordCodeService.updateStatusCode(employeeOptional.get().getLogChangePassword());
        }
        LogChangePassword logChangePassword = startProcess(employeeResult, waitingStatus, webLogPasswordCodeService);
        log.info("Enviando código para o E-mail");
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("code", logChangePassword.getCode());
        MimeMessage mimeMessage = gcEmailService.createMimeMessage(System.getenv("EMAIL_GCSYSTEM"), email, gcEmailService.getSubjectEmail(), bodyParams);
        gcEmailService.send(mimeMessage);
        log.info("Código enviado para o E-mail");
        return true;
    }
    @Override
    public LogChangePassword getLogChangePassword(String email, Integer statusID) throws EntityNotFoundException {
        log.info("Tentativa de validação de código (EMPLOYEE)");
        Optional<LogChangePassword> passwordCode = webLogPasswordCodeService.findPasswordChangeRequestEmployee(email, statusID);
        return passwordCode.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_DATA_INVALID", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public TokenDTO validateCode(String email, String code)
    {
        log.info("Validando token (EMPLOYEE)");
        return validateCode(email, code, webStatusService, webLogPasswordCodeService);
    }

    @Override
    public TokenDTO createTokenDTO(LogChangePassword logChangePassword, String email) {
        log.info("Criando token");
        return TokenDTO.builder()
                .type(String.valueOf(TypeUserEnum.EMPLOYEE))
                .token(createTokenChangePassword(email, logChangePassword.getId(), logChangePassword.getCode())).build();
    }

    @Override
    @Transactional
    public void changePassword(String token, String newPassword) {
        log.info("Atualizando senha");
        Status statusValid = webStatusService.findByName(STATUS_VALID);
        Status statusRescued = webStatusService.findByName(STATUS_RESCUED);
        Optional<Employee> employeeOptional = verifyTokenForChangePassword(token, employeeRepository, statusValid.getId());
        Employee employee = employeeOptional.orElseThrow(() -> new CodeChangePasswordInvalidException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        employee.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        employeeRepository.save(employee);
        employee.getLogChangePassword().forEach(it -> it.setStatus(statusRescued));
        webLogPasswordCodeService.updateStatusCode(employee.getLogChangePassword());
        log.info("Senha atualizada com sucesso");
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }
}