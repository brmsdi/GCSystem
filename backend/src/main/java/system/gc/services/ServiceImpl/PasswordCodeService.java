package system.gc.services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.dtos.EmployeeDTO;
import system.gc.entities.PasswordCode;
import system.gc.repositories.PasswordCodeRepository;

import javax.transaction.Transactional;

@Service
public class PasswordCodeService {
    @Autowired
    PasswordCodeRepository passwordCodeRepository;

    @Transactional
    public PasswordCode save(PasswordCode passwordCode) {
        return passwordCodeRepository.save(passwordCode);
    }
}
