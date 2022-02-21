package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.TypeProblem;

import java.util.Optional;

public interface TypeProblemRepository extends JpaRepository<TypeProblem, Integer> {

    @Query("SELECT typeproblem FROM TypeProblem typeproblem WHERE typeproblem.name LIKE :name")
    Optional<TypeProblem> findByName(String name);
}
