package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.ActivityType;

import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Integer> {

    Optional<ActivityType> findByName(String name);
}
