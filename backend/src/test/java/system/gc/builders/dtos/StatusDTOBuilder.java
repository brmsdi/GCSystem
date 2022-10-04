package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import java.lang.String;
import system.gc.dtos.StatusDTO;

public class StatusDTOBuilder {
	private StatusDTO statusDTO;
	private StatusDTOBuilder(){}

	public static StatusDTOBuilder newInstance() {
		StatusDTOBuilder builder = new StatusDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(StatusDTOBuilder builder) {
		builder.statusDTO = new StatusDTO();
		StatusDTO statusDTO = builder.statusDTO;
		
		statusDTO.setId(0);
		statusDTO.setName("");
	}

	public StatusDTOBuilder withId(Integer param) {
		statusDTO.setId(param);
		return this;
	}

	public StatusDTOBuilder withName(String param) {
		statusDTO.setName(param);
		return this;
	}

	public StatusDTOBuilder nowOpenStatus()
	{
		this.statusDTO.setId(1);
		this.statusDTO.setName("Aberto");
		return this;
	}

	public StatusDTO now() {
		return statusDTO;
	}

}