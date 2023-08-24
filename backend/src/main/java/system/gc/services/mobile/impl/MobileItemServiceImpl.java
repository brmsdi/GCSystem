package system.gc.services.mobile.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.entities.Item;
import system.gc.repositories.ItemRepository;
import system.gc.services.mobile.MobileItemService;

import javax.persistence.EntityNotFoundException;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
public class MobileItemServiceImpl implements MobileItemService {
    private final ItemRepository itemRepository;
    private final MessageSource messageSource;

    @Autowired
    public MobileItemServiceImpl(ItemRepository itemRepository, MessageSource messageSource) {
        this.itemRepository = itemRepository;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        itemRepository.deleteById(item.getId());
    }

    @Override
    @Transactional
    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
