package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.entities.PasswordCode;
import system.gc.repositories.PasswordCodeRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
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

    public void validateCode(String email, String code) {
        log.info("Validando código");
        Optional<PasswordCode> passwordCodeOptional = passwordCodeRepository.findPasswordChangeRequestEmployee(email, statusService.findByName("Aguardando").getId());
        passwordCodeOptional.orElseThrow(() -> new EntityNotFoundException("Dados invalidos!"));
        PasswordCode passwordCode = passwordCodeOptional.get();
        Date currentDate = new Date();
        Date passwordCodeDate = passwordCode.getDate();
        long time = currentDate.getTime() - passwordCodeDate.getTime();
        long minutesPast = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS);
        if (minutesPast <= 5 && passwordCode.getNumberOfAttempts() < 3) {
            if (passwordCode.getCode().equals(code)) {
                passwordCode.setStatus(statusService.findByName("Valido"));
                log.info("Código valido");
            } else {
                log.info("Tentativa invalida");
                short attempts = passwordCode.getNumberOfAttempts();
                attempts++;
                passwordCode.setNumberOfAttempts(attempts);
                if (attempts == 3) {
                    invalidateCode(passwordCode);
                    //Lançar exceção
                }
            }
            save(passwordCode);
        } else {
            invalidateCode(passwordCode);
            save(passwordCode);
            // CRIAR EXCEÇÃO
        }
    }

    private void invalidateCode(PasswordCode passwordCode) {
        log.info("Código invalido");
        passwordCode.setStatus(statusService.findByName("Invalido"));
    }
}
