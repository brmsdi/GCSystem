package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import java.lang.String;
import system.gc.dtos.RoleDTO;

public class RoleDTOBuilder {
	private RoleDTO roleDTO;
	private RoleDTOBuilder(){}

	public static RoleDTOBuilder newInstance() {
		RoleDTOBuilder builder = new RoleDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(RoleDTOBuilder builder) {
		builder.roleDTO = new RoleDTO();
		RoleDTO roleDTO = builder.roleDTO;
		
		roleDTO.setId(0);
		roleDTO.setName("");
	}

	public RoleDTOBuilder withId(Integer param) {
		roleDTO.setId(param);
		return this;
	}

	public RoleDTOBuilder withName(String param) {
		roleDTO.setName(param);
		return this;
	}

	public RoleDTO now() {
		return roleDTO;
	}
}