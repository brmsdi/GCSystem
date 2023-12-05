package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Condominium;

import java.util.List;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface CondominiumRepository extends JpaRepository<Condominium, Integer> {
    @Query("SELECT condominium FROM Condominium condominium " +
            "JOIN FETCH condominium.status " +
            "JOIN FETCH condominium.localizationCondominium localizationCondominium " +
            "JOIN FETCH localizationCondominium.localization " +
            "WHERE condominium IN :condominiumsList")
    List<Condominium> loadLazyCondominiums(List<Condominium> condominiumsList);

    @Query("SELECT condominium FROM Condominium condominium WHERE condominium.name LIKE :name")
    Page<Condominium> findAllByName(Pageable pageable, String name);

    @Query("SELECT DISTINCT condominium FROM Condominium condominium " +
            "JOIN FETCH condominium.contract contract " +
            "JOIN FETCH contract.lessee lessee " +
            "WHERE lessee.id = :idLessee")
    List<Condominium> findAllForLessee(Integer idLessee);
}
