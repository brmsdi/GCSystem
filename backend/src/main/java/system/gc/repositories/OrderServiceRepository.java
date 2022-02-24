package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.OrderService;

import java.util.List;

public interface OrderServiceRepository extends JpaRepository<OrderService, Integer> {
    @Query("SELECT orderservice FROM OrderService orderservice " +
            "JOIN FETCH orderservice.repairRequests repairRequests " +
            "JOIN FETCH repairRequests.typeProblem " +
            "JOIN FETCH repairRequests.lessee " +
            "JOIN FETCH repairRequests.condominium " +
            "JOIN FETCH orderservice.employees " +
            "JOIN FETCH orderservice.status " +
            "WHERE orderservice IN :orderServices")
    List<OrderService> loadLazyOrders(List<OrderService> orderServices);

    @Deprecated
    @Query("SELECT orderservice FROM OrderService orderservice " +
            "JOIN FETCH orderservice.repairRequests repairRequests " +
            "JOIN FETCH repairRequests.lessee lessee " +
            "WHERE lessee.cpf LIKE :cpf")
    List<OrderService> findOrderServiceForLessee(String cpf);
}
