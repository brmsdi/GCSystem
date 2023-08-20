package system.gc.services.web.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.gc.dtos.TokenDTO;
import system.gc.entities.Lessee;
import system.gc.entities.LogChangePassword;
import system.gc.entities.Status;
import system.gc.exceptionsAdvice.exceptions.CodeChangePasswordInvalidException;
import system.gc.repositories.LesseeRepository;
import system.gc.services.web.LogPasswordCode;
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
@Service
public class LogPasswordCodeLesseeImpl implements LogPasswordCode<Lessee, LesseeRepository> {

    @Autowired
    private LesseeRepository lesseeRepository;
    @Autowired
    private GCEmailService gcEmailService;

    @Autowired
    private LogPasswordCodeService logPasswordCodeService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean generateCodeForChangePassword(String email)
    {
        log.info("Iniciando processo de geração de codigo para troca de senha");
        Status waitingStatus = statusService.findByName(STATUS_WAITING);
        Status cancelStatus = statusService.findByName(STATUS_CANCELED);
        Lessee lesseeResult = verifyEmail(email, lesseeRepository);
        Optional<Lessee> lesseeOptional = checkIfThereISAnOpenRequest(lesseeResult.getId(), lesseeRepository, waitingStatus.getId());
        if (lesseeOptional.isPresent()) {
            lesseeOptional.get().getLogChangePassword().forEach(it -> it.setStatus(cancelStatus));
            logPasswordCodeService.updateStatusCode(lesseeOptional.get().getLogChangePassword());
        }
        LogChangePassword logChangePassword = startProcess(lesseeResult, statusService.findByName(STATUS_WAITING), logPasswordCodeService);
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
        log.info("Tentativa de validação de código (LESSEE)");
        Optional<LogChangePassword> passwordCode = logPasswordCodeService.findPasswordChangeRequestEmployee(email, statusID);
        return passwordCode.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_DATA_INVALID", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public TokenDTO validateCode(String email, String code)
    {
        log.info("Validando token (LESSEE)");
        return validateCode(email, code, statusService, logPasswordCodeService);
    }

    @Override
    public TokenDTO createTokenDTO(LogChangePassword logChangePassword, String email) {
        log.info("Criando token");
        return TokenDTO.builder()
                .type(String.valueOf(TypeUserEnum.LESSEE))
                .token(createTokenChangePassword(email, logChangePassword.getId(), logChangePassword.getCode())).build();
    }

    @Override
    public void changePassword(String token, String newPassword) {
        log.info("Atualizando senha");
        Status statusValid = statusService.findByName(STATUS_VALID);
        Status statusRescued = statusService.findByName(STATUS_RESCUED);
        Optional<Lessee> lesseeOptional = verifyTokenForChangePassword(token, lesseeRepository, statusValid.getId());
        Lessee lessee = lesseeOptional.orElseThrow(() -> new CodeChangePasswordInvalidException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        lessee.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        lesseeRepository.save(lessee);
        lessee.getLogChangePassword().forEach(it -> it.setStatus(statusRescued));
        logPasswordCodeService.updateStatusCode(lessee.getLogChangePassword());
        log.info("Senha atualizada com sucesso");
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }
}