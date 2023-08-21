package system.gc.services.mobile.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.ItemDTO;
import system.gc.entities.Item;
import system.gc.entities.RepairRequest;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;
import system.gc.repositories.RepairRequestRepository;
import system.gc.services.mobile.MobileItemService;
import system.gc.services.mobile.MobileRepairRequestService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static system.gc.utils.TextUtils.STATUS_CONCLUDED;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
@Log4j2
public class MobileRepairRequestServiceImpl implements MobileRepairRequestService {

    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @Autowired
    private MobileItemService mobileItemService;
    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public RepairRequest findRepairRequestToAddOrRemoveItem(Integer id) {
        Optional<RepairRequest> optionalRepairRequest = repairRequestRepository.findRepairRequestToAddOrRemoveItem(id);
        return optionalRepairRequest.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REPAIR_REQUEST_NOT_FOUND", null, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional
    public ItemDTO addItem(Integer idEmployee, Integer idRepairRequest, ItemDTO itemDTO) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException {
        log.info("Adicionando item ao reparo");
        RepairRequest repairRequest = findRepairRequestToAddOrRemoveItem(idRepairRequest);
        if (repairRequest.getOrderService().getStatus().getName().equalsIgnoreCase(STATUS_CONCLUDED)) {
            throw new IllegalChangeOrderServiceException(messageSource.getMessage("TEXT_ERROR_ORDER_SERVICE_STATUS_CONCLUDED", null, LocaleContextHolder.getLocale()));
        }
        boolean isResponsible = isResponsible(idEmployee, repairRequest.getOrderService().getEmployees());
        if (!isResponsible) {
            throw new AccessDeniedOrderServiceException(messageSource.getMessage("ACCESS_DENIED", null, LocaleContextHolder.getLocale()));
        }
        return new ItemDTO(mobileItemService.save(ItemDTO.toEntityWithRepairRequest(itemDTO, repairRequest)));
    }

    @Override
    @Transactional
    public void removeItem(Integer idEmployee, Integer idRepairRequest, Integer idItem) throws AccessDeniedOrderServiceException, EntityNotFoundException {
        log.info("Removendo item do reparo");
        RepairRequest repairRequest = findRepairRequestToAddOrRemoveItem(idRepairRequest);
        boolean isResponsible = isResponsible(idEmployee, repairRequest.getOrderService().getEmployees());
        if (!isResponsible) {
            throw new AccessDeniedOrderServiceException(messageSource.getMessage("ACCESS_DENIED", null, LocaleContextHolder.getLocale()));
        }
        Optional<Item> optionalItem = getItemInItems(idItem, repairRequest.getItems());
        Item item = optionalItem.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        mobileItemService.delete(item);
    }
}