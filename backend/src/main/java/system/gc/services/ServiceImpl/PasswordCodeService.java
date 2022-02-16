package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import system.gc.configuration.exceptions.CodeChangePasswordInvalidException;
import system.gc.dtos.TokenDTO;
import system.gc.entities.PasswordCode;
import system.gc.entities.Status;
import system.gc.repositories.PasswordCodeRepository;
import system.gc.security.token.JWTService;
import system.gc.utils.TextUtils;
import system.gc.utils.TypeUserEnum;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PasswordCodeService {
    @Autowired
    PasswordCodeRepository passwordCodeRepository;

    @Autowired
    StatusService statusService;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public PasswordCode save(PasswordCode passwordCode) {
        return passwordCodeRepository.save(passwordCode);
    }

    public TokenDTO validateCode(String email, Integer type, String code) {
        log.info("Validando código");
        PasswordCode passwordCode = chooseType(email, type);
        Date currentDate = new Date();
        Date passwordCodeDate = passwordCode.getDate();
        long time = currentDate.getTime() - passwordCodeDate.getTime();
        long minutesPast = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS);
        if (minutesPast <= 5 && passwordCode.getNumberOfAttempts() < 3) {
            if (passwordCode.getCode().equals(code)) {
                passwordCode.setStatus(statusService.findByName("Valido"));
                log.info("Código valido");
                save(passwordCode);
                return TokenDTO.builder()
                        .type("changePassword")
                        .token(createTokenChangePassword(email, passwordCode.getId(), passwordCode.getCode())).build();
            } else {
                log.info("Tentativa invalida");
                short attempts = passwordCode.getNumberOfAttempts();
                attempts++;
                passwordCode.setNumberOfAttempts(attempts);
                if (attempts == 3) {
                    invalidateCode(passwordCode);
                    throw new CodeChangePasswordInvalidException("Excedeu o número de tentativas");
                }
            }
            save(passwordCode);
            throw new CodeChangePasswordInvalidException(messageSource.getMessage("TEXT_ERROR_CODE_INVALID",
                    null, LocaleContextHolder.getLocale()));
        }
        invalidateCode(passwordCode);
        throw new CodeChangePasswordInvalidException("As informações não correspondem. Solicite outro código!");
    }
    
    private PasswordCode chooseType(String email, Integer type) {
        Optional<PasswordCode> passwordCode;
        Status WaitingStatus = statusService.findByName("Aguardando");
        if (TypeUserEnum.valueOf(type) == TypeUserEnum.EMPLOYEE) {
            log.info("Tentativa de validação de código (EMPLOYEE)");
            passwordCode = passwordCodeRepository.findPasswordChangeRequestEmployee(email, WaitingStatus.getId());
            return passwordCode.orElseThrow(() -> new EntityNotFoundException("Dados invalidos!"));
        } else if (TypeUserEnum.valueOf(type) == TypeUserEnum.LESSEE) {
            log.info("Tentativa de validação de código (LESSEE)");
            passwordCode = passwordCodeRepository.findPasswordChangeRequestLessee(email, WaitingStatus.getId()) ;
            return passwordCode.orElseThrow(() -> new EntityNotFoundException("Dados invalidos!"));
        }
        throw new EntityNotFoundException("Dados invalidos!");
    }
    
    private void invalidateCode(PasswordCode passwordCode) {
        log.info("Código invalido");
        passwordCode.setStatus(statusService.findByName("Invalido"));
        save(passwordCode);
    }

    public void cancelCode(Iterable<PasswordCode> passwordCodes) {
        log.info("Cancelando código...");
        passwordCodeRepository.saveAll(passwordCodes);
    }

    private String createTokenChangePassword(String email, Integer ID, String code) {
        log.info("Criando token de troca de senha");
        Map<String, String> params = new HashMap<>();
        params.put("EMAIL", email);
        params.put("ID", String.valueOf(ID));
        params.put("CODE", code);
        return JWTService.createTokenJWT(params, TextUtils.TIME_TOKEN_CHANGE_PASSWORD_EXPIRATION);
    }
}
