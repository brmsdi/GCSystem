package system.gc.services.mobile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.entities.Item;
import system.gc.repositories.ItemRepository;
import system.gc.services.mobile.MobileItemService;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
public class MobileItemServiceImpl implements MobileItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
