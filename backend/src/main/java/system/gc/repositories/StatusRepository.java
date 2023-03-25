package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Status;

import java.util.List;
import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface StatusRepository extends JpaRepository<Status, Integer> {

    Optional<Status> findByName(String name);

    @Query("SELECT status FROM Status status WHERE status.name IN :statusList order by status.name")
    List<Status> findAllToView(List<String> statusList);
}
