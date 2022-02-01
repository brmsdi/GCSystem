package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.Localization;

public interface LocalizationRepository extends JpaRepository<Localization, Integer> {
}
