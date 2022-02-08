package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.ActivityType;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Integer> {
}
