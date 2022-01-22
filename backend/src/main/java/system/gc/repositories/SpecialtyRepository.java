package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    
}
