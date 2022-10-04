package system.gc.builders.dtos;

import java.util.Arrays;
import java.lang.String;
import system.gc.dtos.LocalizationDTO;

public class LocalizationDTOBuilder {
	private LocalizationDTO localizationDTO;
	private LocalizationDTOBuilder(){}

	public static LocalizationDTOBuilder newInstance() {
		LocalizationDTOBuilder builder = new LocalizationDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(LocalizationDTOBuilder builder) {
		builder.localizationDTO = new LocalizationDTO();
		LocalizationDTO localizationDTO = builder.localizationDTO;
		
		localizationDTO.setZipCode(0);
		localizationDTO.setName("");
		localizationDTO.setRoad("");
	}

	public LocalizationDTOBuilder withZipCode(int param) {
		localizationDTO.setZipCode(param);
		return this;
	}

	public LocalizationDTOBuilder withName(String param) {
		localizationDTO.setName(param);
		return this;
	}

	public LocalizationDTOBuilder withRoad(String param) {
		localizationDTO.setRoad(param);
		return this;
	}

	public LocalizationDTO now() {
		return localizationDTO;
	}
}