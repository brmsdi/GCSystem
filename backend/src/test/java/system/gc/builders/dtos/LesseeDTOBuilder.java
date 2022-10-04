package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import java.lang.String;
import java.util.Set;
import java.util.Date;
import system.gc.dtos.StatusDTO;
import system.gc.dtos.LesseeDTO;

public class LesseeDTOBuilder {
	private LesseeDTO lesseeDTO;
	private LesseeDTOBuilder(){}

	public static LesseeDTOBuilder newInstance() {
		LesseeDTOBuilder builder = new LesseeDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(LesseeDTOBuilder builder) {
		builder.lesseeDTO = new LesseeDTO();
		LesseeDTO lesseeDTO = builder.lesseeDTO;
		
		lesseeDTO.setId(0);
		lesseeDTO.setName("");
		lesseeDTO.setRg("");
		lesseeDTO.setCpf("");
		lesseeDTO.setBirthDate(null);
		lesseeDTO.setEmail("");
		lesseeDTO.setContactNumber("");
		lesseeDTO.setPassword("");
		lesseeDTO.setStatus(null);
		lesseeDTO.setDebts(null);
	}

	public LesseeDTOBuilder withId(Integer param) {
		lesseeDTO.setId(param);
		return this;
	}

	public LesseeDTOBuilder withName(String param) {
		lesseeDTO.setName(param);
		return this;
	}

	public LesseeDTOBuilder withRg(String param) {
		lesseeDTO.setRg(param);
		return this;
	}

	public LesseeDTOBuilder withCpf(String param) {
		lesseeDTO.setCpf(param);
		return this;
	}

	public LesseeDTOBuilder withBirthDate(Date param) {
		lesseeDTO.setBirthDate(param);
		return this;
	}

	public LesseeDTOBuilder withEmail(String param) {
		lesseeDTO.setEmail(param);
		return this;
	}

	public LesseeDTOBuilder withContactNumber(String param) {
		lesseeDTO.setContactNumber(param);
		return this;
	}

	public LesseeDTOBuilder withPassword(String param) {
		lesseeDTO.setPassword(param);
		return this;
	}

	public LesseeDTOBuilder withStatus(StatusDTO param) {
		lesseeDTO.setStatus(param);
		return this;
	}

	public LesseeDTOBuilder withDebts(Set param) {
		lesseeDTO.setDebts(param);
		return this;
	}

	public LesseeDTO now() {
		return lesseeDTO;
	}
}