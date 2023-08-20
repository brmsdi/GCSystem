package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.Item;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
