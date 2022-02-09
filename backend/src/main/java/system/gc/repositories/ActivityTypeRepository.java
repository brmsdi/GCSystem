package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.ActivityType;
import java.util.Optional;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Integer> {
    @Query("SELECT activity FROM ActivityType activity WHERE activity.name like :name")
    Optional<ActivityType> getByName(String name);
}
