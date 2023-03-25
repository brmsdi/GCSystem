package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Contract;

import java.util.List;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    @Query("SELECT contract FROM Contract contract " +
            "JOIN FETCH contract.status " +
            "JOIN FETCH contract.condominium condominium " +
            "JOIN FETCH condominium.localizationCondominium localizationCondominium " +
            "JOIN FETCH localizationCondominium.localization " +
            "JOIN FETCH contract.lessee " +
            "WHERE contract IN :contractList")
    List<Contract> loadLazyContracts(List<Contract> contractList);

    @Query("SELECT contract FROM Contract contract WHERE contract.lessee.id = :id")
    Page<Contract> findContractsForLessee(Pageable pageable, Integer id);
}
