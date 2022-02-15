package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.configuration.exceptions.CodeChangePasswordInvalidException;
import system.gc.entities.PasswordCode;
import system.gc.repositories.PasswordCodeRepository;
import system.gc.utils.TypeUserEnum;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PasswordCodeService {
    @Autowired
    PasswordCodeRepository passwordCodeRepository;

    @Autowired
    StatusService statusService;

    @Transactional
    public PasswordCode save(PasswordCode passwordCode) {
        return passwordCodeRepository.save(passwordCode);
    }

    @Transactional
    public boolean validateCode(String email, Integer type, String code) {
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
                return true;
            } else {
                log.info("Tentativa invalida");
                short attempts = passwordCode.getNumberOfAttempts();
                attempts++;
                passwordCode.setNumberOfAttempts(attempts);
                if (attempts == 3) {
                    invalidateCode(passwordCode, "Excedeu o número de tentativas");
                }
            }
            save(passwordCode);
            return false;
        }
        invalidateCode(passwordCode, "Código invalido.");
        return false;
    }
    
    private PasswordCode chooseType(String email, Integer type) {
        Optional<PasswordCode> passwordCode;
        if (TypeUserEnum.valueOf(type) == TypeUserEnum.EMPLOYEE) {
            log.info("Tentativa de validação de código (EMPLOYEE)");
            passwordCode = passwordCodeRepository.findPasswordChangeRequestEmployee(email, statusService.findByName("Aguardando").getId()) ;
            return passwordCode.orElseThrow(() -> new EntityNotFoundException("Dados invalidos!"));
        } else if (TypeUserEnum.valueOf(type) == TypeUserEnum.LESSEE) {
            log.info("Tentativa de validação de código (LESSEE)");
            passwordCode = passwordCodeRepository.findPasswordChangeRequestLessee(email, statusService.findByName("Aguardando").getId()) ;
            return passwordCode.orElseThrow(() -> new EntityNotFoundException("Dados invalidos!"));
        }
        throw new EntityNotFoundException("Dados invalidos!");
    }
    
    private void invalidateCode(PasswordCode passwordCode, String message) {
        log.info("Código invalido");
        passwordCode.setStatus(statusService.findByName("Invalido"));
        save(passwordCode);
        throw new CodeChangePasswordInvalidException(message);
    }

    public void cancelCode(Iterable<PasswordCode> passwordCodes) {
        log.info("Cancelando código...");
        passwordCodeRepository.saveAll(passwordCodes);

    }
}
