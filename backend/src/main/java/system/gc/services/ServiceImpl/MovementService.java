package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.dtos.DebtDTO;
import system.gc.entities.ActivityType;
import system.gc.entities.Debt;
import system.gc.entities.Employee;
import system.gc.entities.Movement;
import system.gc.repositories.MovementRepository;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Service
@Slf4j
public class MovementService {

    @Autowired
    private MovementRepository movementRepository;

    public void registerMovement(Debt debt, ActivityType activityType, Employee employee) {
        log.info("Registrando movimentação");
        movementRepository.save(new Movement(new Date(),
                debt.getDueDate(),
                debt.getValue(),
                debt,
                activityType,
                employee));
        log.info("Movimentaçao registrada!");
    }

    public void registerMovement(DebtDTO previousDebtDTO, Debt debt, ActivityType activityType, Employee employee) {
        log.info("Registrando movimentação");
        movementRepository.save(new Movement(new Date(),
                debt.getDueDate(),
                previousDebtDTO.getValue(),
                debt,
                activityType,
                employee));
        log.info("Movimentaçao registrada!");
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        movementRepository.deleteAll();
    }
}
