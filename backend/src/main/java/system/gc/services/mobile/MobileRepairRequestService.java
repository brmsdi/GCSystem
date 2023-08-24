package system.gc.services.mobile;

import system.gc.dtos.ItemDTO;
import system.gc.entities.Item;
import system.gc.entities.RepairRequest;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileRepairRequestService extends MobileOrderServiceStatusUtils, MobileEmployeeResponsibility {

    RepairRequest findRepairRequestToAddOrRemoveItem(Integer id);

    default boolean containsItem(Integer idItem, Set<Item> items) {
        return items.stream().map(Item::getId)
                .collect(Collectors.toSet())
                .contains(idItem);
    }

    default Optional<Item> getItemInItems(Integer idItem, Set<Item> items) {
        return items.stream()
                .filter(item -> Objects.equals(item.getId(), idItem))
                .findFirst();
    }

    ItemDTO addItem(Integer idEmployee, Integer idRepairRequest, ItemDTO itemDTO) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException;

    void removeItem(Integer idEmployee, Integer idRepairRequest, Integer idItem) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException;
}

