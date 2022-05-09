package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Status;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    @Query("SELECT status FROM Status status WHERE status.name like :name")
    Optional<Status> findByName(String name);

    @Query("SELECT status FROM Status status WHERE status.name IN :statusList order by status.name")
    List<Status> findAllToView(List<String> statusList);
}
