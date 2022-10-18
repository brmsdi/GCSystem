package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.TypeProblem;
import java.util.Optional;

public interface TypeProblemRepository extends JpaRepository<TypeProblem, Integer> {

    Optional<TypeProblem> findByName(String name);
}
