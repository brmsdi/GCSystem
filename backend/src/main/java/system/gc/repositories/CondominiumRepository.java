package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.Condominium;

public interface CondominiumRepository extends JpaRepository<Condominium, Integer> {
}
