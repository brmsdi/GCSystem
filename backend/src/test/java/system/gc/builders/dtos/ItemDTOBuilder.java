package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import java.lang.String;
import system.gc.dtos.ItemDTO;

public class ItemDTOBuilder {
	private ItemDTO itemDTO;
	private ItemDTOBuilder(){}

	public static ItemDTOBuilder newInstance() {
		ItemDTOBuilder builder = new ItemDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(ItemDTOBuilder builder) {
		builder.itemDTO = new ItemDTO();
		ItemDTO itemDTO = builder.itemDTO;
		
		itemDTO.setId(0);
		itemDTO.setDescription("");
		itemDTO.setQuantity(0);
		itemDTO.setValue(0.0);
	}

	public ItemDTOBuilder withId(Integer param) {
		itemDTO.setId(param);
		return this;
	}

	public ItemDTOBuilder withDescription(String param) {
		itemDTO.setDescription(param);
		return this;
	}

	public ItemDTOBuilder withQuantity(int param) {
		itemDTO.setQuantity(param);
		return this;
	}

	public ItemDTOBuilder withValue(double param) {
		itemDTO.setValue(param);
		return this;
	}

	public ItemDTO now() {
		return itemDTO;
	}
}