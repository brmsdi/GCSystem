package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.OrderService;

public interface OrderServiceRepository extends JpaRepository<OrderService, Integer> {}
