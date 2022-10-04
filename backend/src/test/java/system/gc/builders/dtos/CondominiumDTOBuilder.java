package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import java.lang.String;
import system.gc.dtos.LocalizationCondominiumDTO;
import system.gc.dtos.StatusDTO;
import system.gc.dtos.CondominiumDTO;

public class CondominiumDTOBuilder {
	private CondominiumDTO condominiumDTO;
	private CondominiumDTOBuilder(){}

	public static CondominiumDTOBuilder newInstance() {
		CondominiumDTOBuilder builder = new CondominiumDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(CondominiumDTOBuilder builder) {
		builder.condominiumDTO = new CondominiumDTO();
		CondominiumDTO condominiumDTO = builder.condominiumDTO;
		
		condominiumDTO.setId(0);
		condominiumDTO.setName("");
		condominiumDTO.setDescription("");
		condominiumDTO.setNumberApartments(0);
		condominiumDTO.setStatus(null);
		condominiumDTO.setLocalization(null);
	}

	public CondominiumDTOBuilder withId(Integer param) {
		condominiumDTO.setId(param);
		return this;
	}

	public CondominiumDTOBuilder withName(String param) {
		condominiumDTO.setName(param);
		return this;
	}

	public CondominiumDTOBuilder withDescription(String param) {
		condominiumDTO.setDescription(param);
		return this;
	}

	public CondominiumDTOBuilder withNumberApartments(int param) {
		condominiumDTO.setNumberApartments(param);
		return this;
	}

	public CondominiumDTOBuilder withStatus(StatusDTO param) {
		condominiumDTO.setStatus(param);
		return this;
	}

	public CondominiumDTOBuilder withLocalization(LocalizationCondominiumDTO param) {
		condominiumDTO.setLocalization(param);
		return this;
	}

	public CondominiumDTO now() {
		return condominiumDTO;
	}
}