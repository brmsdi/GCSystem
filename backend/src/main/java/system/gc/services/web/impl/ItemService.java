package system.gc.services.web.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.entities.Item;
import system.gc.repositories.ItemRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public List<Item> saveAll(Set<Item> items) {
        return itemRepository.saveAll(items);
    }
}
