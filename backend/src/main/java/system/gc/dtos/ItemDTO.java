package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Item;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
public class ItemDTO implements ConvertEntityAndDTO<ItemDTO, Item> {
    private Integer Id;
    private String description;
    private int quantity;
    private double value;

    public ItemDTO() {}

    public ItemDTO(String description, int quantity, double value) {
        setDescription(description);
        setQuantity(quantity);
        setValue(value);
    }

    public ItemDTO(Item item) {
        setId(item.getId());
        setDescription(item.getDescription());
        setQuantity(item.getQuantity());
        setValue(item.getValue());
    }

    @Override
    public ItemDTO toDTO(Item item) {
        return new ItemDTO(item);
    }

    @Override
    public Item toEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setDescription(itemDTO.getDescription());
        item.setQuantity(itemDTO.getQuantity());
        item.setValue(itemDTO.getValue());
        if (itemDTO.getId() != null) {
            item.setId(itemDTO.getId());
        }
        return item;
    }
}
