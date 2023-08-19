package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.OrderService;

import java.util.List;
import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface OrderServiceRepository extends JpaRepository<OrderService, Integer> {
    @Query("SELECT os FROM OrderService os " +
            "JOIN FETCH os.employees " +
            "JOIN FETCH os.status " +
            "JOIN FETCH os.repairRequests repairRequests " +
            "JOIN FETCH repairRequests.typeProblem " +
            "JOIN FETCH repairRequests.lessee " +
            "JOIN FETCH repairRequests.condominium " +
            "JOIN FETCH repairRequests.items " +
            "JOIN FETCH repairRequests.status " +
            "WHERE os IN :orderServices")
    List<OrderService> loadLazyOrders(List<OrderService> orderServices);

    @Deprecated
    @Query("SELECT orderservice FROM OrderService orderservice " +
            "JOIN FETCH orderservice.repairRequests repairRequests " +
            "JOIN FETCH repairRequests.lessee lessee " +
            "WHERE lessee.cpf LIKE :cpf")
    List<OrderService> findOrderServiceForLessee(String cpf);


    @Query("SELECT os FROM OrderService os " +
            "LEFT JOIN FETCH os.repairRequests repairRequests " +
            "LEFT JOIN FETCH repairRequests.typeProblem " +
            "LEFT JOIN FETCH repairRequests.lessee " +
            "LEFT JOIN FETCH repairRequests.condominium " +
            "LEFT JOIN FETCH repairRequests.status " +
            "LEFT JOIN FETCH repairRequests.items " +
            "LEFT JOIN FETCH os.employees " +
            "LEFT JOIN FETCH os.status " +
            "WHERE os.id = :idOrderService")
    Optional<OrderService> details(Integer idOrderService);

    @Query("SELECT DISTINCT os FROM OrderService os " +
            "INNER JOIN os.employees e " +
            "WHERE e.id = :idEmployee")
    Page<OrderService> findAllByEmployee(Pageable pageable, Integer idEmployee);

    @Query("SELECT os FROM OrderService os " +
            "JOIN FETCH os.status " +
            "WHERE os IN :orderServices")
    List<OrderService> loadLazyWithStatus(List<OrderService> orderServices);
}
