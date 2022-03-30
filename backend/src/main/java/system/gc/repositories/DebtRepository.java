package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Debt;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Integer> {

    @Query("SELECT debt FROM Debt debt " +
            "JOIN FETCH debt.status " +
            "JOIN FETCH debt.movements movements " +
            "JOIN FETCH movements.activityType " +
            "JOIN FETCH movements.employee employee " +
            "JOIN FETCH employee.role " +
            "JOIN FETCH employee.status " +
            "JOIN FETCH debt.lessee lessee " +
            "JOIN FETCH lessee.status " +
            "WHERE debt IN :debts")
    List<Debt> loadLazyDebts(List<Debt> debts);

    @Query("SELECT debt FROM Debt debt WHERE debt.lessee.id = :id")
    Page<Debt> findDebtsForLessee(Pageable pageable, Integer id);
}
