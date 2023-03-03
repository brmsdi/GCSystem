package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.Movement;
@Deprecated
public interface MovementRepository extends JpaRepository<Movement, Integer> {
}
