package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.ActivityType;

import java.util.Optional;
@Deprecated
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Integer> {

    Optional<ActivityType> findByName(String name);
}
