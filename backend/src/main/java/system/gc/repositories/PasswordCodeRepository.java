package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.PasswordCode;

public interface PasswordCodeRepository extends JpaRepository<PasswordCode, Integer> {
}
