package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import system.gc.dtos.RoleDTO;
import java.lang.String;
import java.util.Set;
import java.util.Date;
import system.gc.dtos.StatusDTO;
import system.gc.dtos.EmployeeDTO;

public class EmployeeDTOBuilder {
	private EmployeeDTO employeeDTO;
	private EmployeeDTOBuilder(){}

	public static EmployeeDTOBuilder newInstance() {
		EmployeeDTOBuilder builder = new EmployeeDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(EmployeeDTOBuilder builder) {
		builder.employeeDTO = new EmployeeDTO();
		EmployeeDTO employeeDTO = builder.employeeDTO;
		
		employeeDTO.setId(0);
		employeeDTO.setName("");
		employeeDTO.setRg("");
		employeeDTO.setCpf("");
		employeeDTO.setBirthDate(null);
		employeeDTO.setEmail("");
		employeeDTO.setHiringDate(null);
		employeeDTO.setPassword("");
		employeeDTO.setRole(null);
		employeeDTO.setMovements(null);
		employeeDTO.setStatus(null);
	}

	public EmployeeDTOBuilder withId(Integer param) {
		employeeDTO.setId(param);
		return this;
	}

	public EmployeeDTOBuilder withName(String param) {
		employeeDTO.setName(param);
		return this;
	}

	public EmployeeDTOBuilder withRg(String param) {
		employeeDTO.setRg(param);
		return this;
	}

	public EmployeeDTOBuilder withCpf(String param) {
		employeeDTO.setCpf(param);
		return this;
	}

	public EmployeeDTOBuilder withBirthDate(Date param) {
		employeeDTO.setBirthDate(param);
		return this;
	}

	public EmployeeDTOBuilder withEmail(String param) {
		employeeDTO.setEmail(param);
		return this;
	}

	public EmployeeDTOBuilder withHiringDate(Date param) {
		employeeDTO.setHiringDate(param);
		return this;
	}

	public EmployeeDTOBuilder withPassword(String param) {
		employeeDTO.setPassword(param);
		return this;
	}

	public EmployeeDTOBuilder withRole(RoleDTO param) {
		employeeDTO.setRole(param);
		return this;
	}

	public EmployeeDTOBuilder withMovements(Set param) {
		employeeDTO.setMovements(param);
		return this;
	}

	public EmployeeDTOBuilder withStatus(StatusDTO param) {
		employeeDTO.setStatus(param);
		return this;
	}

	public EmployeeDTO now() {
		return employeeDTO;
	}
}