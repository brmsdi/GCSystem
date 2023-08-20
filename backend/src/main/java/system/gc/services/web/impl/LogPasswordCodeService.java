package system.gc.services.web.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import system.gc.dtos.TokenDTO;
import system.gc.entities.LogChangePassword;
import system.gc.entities.Status;
import system.gc.exceptionsAdvice.exceptions.CodeChangePasswordInvalidException;
import system.gc.repositories.LogPasswordCodeRepository;
import system.gc.security.token.JWTService;
import system.gc.services.web.LogPasswordCode;
import system.gc.utils.TextUtils;
import system.gc.utils.TypeUserEnum;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static system.gc.utils.TextUtils.*;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
@Slf4j
public class LogPasswordCodeService {
    @Autowired
    private LogPasswordCodeRepository logPasswordCodeRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public LogChangePassword save(LogChangePassword logChangePassword) {
        return logPasswordCodeRepository.save(logChangePassword);
    }

    @Deprecated(since = "1.3")
    public TokenDTO validateCode(String email, String code, LogPasswordCode logPasswordCode) {
        log.info("Validando código");
        Status waitingStatus = statusService.findByName(STATUS_WAITING);
        LogChangePassword logChangePassword = logPasswordCode.getLogChangePassword(email, waitingStatus.getId());
        Date currentDate = new Date();
        Date passwordCodeDate = logChangePassword.getDate();
        long time = currentDate.getTime() - passwordCodeDate.getTime();
        long minutesPast = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS);
        short attempts = logChangePassword.getNumberOfAttempts();
        attempts++;
        if (minutesPast <= 5 && logChangePassword.getNumberOfAttempts() < 3) {
            if (logChangePassword.getCode().equals(code)) {
                logChangePassword.setStatus(statusService.findByName(STATUS_VALID));
                log.info("Código valido");
                logChangePassword.setNumberOfAttempts(attempts);
                save(logChangePassword);
                return createTokenDTO(logChangePassword, email);
            } else {
                log.info("Tentativa invalida");
                logChangePassword.setNumberOfAttempts(attempts);
                if (attempts == 3) {
                    updateStatusCode(logChangePassword, statusService.findByName(STATUS_INVALID));
                    throw new CodeChangePasswordInvalidException("Excedeu o número de tentativas");
                }
            }
            save(logChangePassword);
            throw new CodeChangePasswordInvalidException(messageSource.getMessage("TEXT_ERROR_CODE_INVALID",
                    null, LocaleContextHolder.getLocale()));
        }
        updateStatusCode(logChangePassword, statusService.findByName(STATUS_INVALID));
        throw new CodeChangePasswordInvalidException("As informações não correspondem. Solicite outro código!");
    }

    public void updateStatusCode(LogChangePassword logChangePassword, Status status) {
        log.info("Atualizando status do registro de troca de senha");
        logChangePassword.setStatus(status);
        save(logChangePassword);
    }

    public void updateStatusCode(Iterable<LogChangePassword> passwordCodes) {
        logPasswordCodeRepository.saveAll(passwordCodes);
    }

    @Deprecated(since = "1.3")
    private String createTokenChangePassword(String email, Integer ID, String code) {
        log.info("Criando token de troca de senha");
        Map<String, String> params = new HashMap<>();
        params.put("EMAIL", email);
        params.put("ID", String.valueOf(ID));
        params.put("CODE", code);
        return JWTService.createTokenJWT(params, TextUtils.TIME_TOKEN_CHANGE_PASSWORD_EXPIRATION);
    }

    @Deprecated(since = "1.3")
    private TokenDTO createTokenDTO(LogChangePassword logChangePassword, String email) {
        if(logChangePassword.getEmployee() != null) {
            return TokenDTO.builder()
                    .type(String.valueOf(TypeUserEnum.EMPLOYEE))
                    .token(createTokenChangePassword(email, logChangePassword.getId(), logChangePassword.getCode())).build();
        }
        return TokenDTO.builder()
                .type(String.valueOf(TypeUserEnum.LESSEE))
                .token(createTokenChangePassword(email, logChangePassword.getId(), logChangePassword.getCode())).build();
    }

    public Optional<LogChangePassword> findPasswordChangeRequestEmployee(String email, Integer statusID)
    {
        return logPasswordCodeRepository.findPasswordChangeRequestEmployee(email, statusID);
    }

    public Optional<LogChangePassword> findPasswordChangeRequestLessee(String email, Integer statusID)
    {
        return logPasswordCodeRepository.findPasswordChangeRequestLessee(email, statusID);
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        logPasswordCodeRepository.deleteAll();
    }
}
