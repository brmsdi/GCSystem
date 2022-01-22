package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
