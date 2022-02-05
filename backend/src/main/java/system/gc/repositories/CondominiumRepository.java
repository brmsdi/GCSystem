package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Condominium;

import java.util.List;

public interface CondominiumRepository extends JpaRepository<Condominium, Integer> {
    @Query("SELECT condominium FROM Condominium condominium JOIN FETCH condominium.status JOIN FETCH condominium.localizationCondominium localizationCondominium JOIN FETCH localizationCondominium.localization WHERE condominium IN :condominiumsList")
    List<Condominium> loadLazyCondominiums(List<Condominium> condominiumsList);

    @Query("SELECT condominium FROM Condominium condominium WHERE condominium.name LIKE :name")
    Page<Condominium> findAllByName(Pageable pageable, String name);
}
