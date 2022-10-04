package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import system.gc.dtos.LocalizationDTO;
import java.lang.String;
import system.gc.dtos.LocalizationCondominiumDTO;

public class LocalizationCondominiumDTOBuilder {
	private LocalizationCondominiumDTO localizationCondominiumDTO;
	private LocalizationCondominiumDTOBuilder(){}

	public static LocalizationCondominiumDTOBuilder newInstance() {
		LocalizationCondominiumDTOBuilder builder = new LocalizationCondominiumDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(LocalizationCondominiumDTOBuilder builder) {
		builder.localizationCondominiumDTO = new LocalizationCondominiumDTO();
		LocalizationCondominiumDTO localizationCondominiumDTO = builder.localizationCondominiumDTO;
		
		localizationCondominiumDTO.setId(0);
		localizationCondominiumDTO.setNumber("");
		localizationCondominiumDTO.setLocalization(null);
	}

	public LocalizationCondominiumDTOBuilder withId(Integer param) {
		localizationCondominiumDTO.setId(param);
		return this;
	}

	public LocalizationCondominiumDTOBuilder withNumber(String param) {
		localizationCondominiumDTO.setNumber(param);
		return this;
	}

	public LocalizationCondominiumDTOBuilder withLocalization(LocalizationDTO param) {
		localizationCondominiumDTO.setLocalization(param);
		return this;
	}

	public LocalizationCondominiumDTO now() {
		return localizationCondominiumDTO;
	}
}