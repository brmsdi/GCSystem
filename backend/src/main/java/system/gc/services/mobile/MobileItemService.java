package system.gc.services.mobile;

import system.gc.entities.Item;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileItemService {
    Item save(Item item);
    void delete(Integer id);
    void delete(Item item);
}
