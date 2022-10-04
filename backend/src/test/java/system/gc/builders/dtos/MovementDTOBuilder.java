package system.gc.builders.dtos;

import java.lang.Integer;
import system.gc.dtos.ActivityTypeDTO;
import java.util.Arrays;
import system.gc.dtos.EmployeeDTO;
import java.util.Date;
import system.gc.dtos.MovementDTO;

public class MovementDTOBuilder {
	private MovementDTO movementDTO;
	private MovementDTOBuilder(){}

	public static MovementDTOBuilder newInstance() {
		MovementDTOBuilder builder = new MovementDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(MovementDTOBuilder builder) {
		builder.movementDTO = new MovementDTO();
		MovementDTO movementDTO = builder.movementDTO;
		
		movementDTO.setId(0);
		movementDTO.setMoveDateAndTime(null);
		movementDTO.setDueDate(null);
		movementDTO.setPreviousValue(0.0);
		movementDTO.setActivityType(null);
		movementDTO.setEmployee(null);
	}

	public MovementDTOBuilder withId(Integer param) {
		movementDTO.setId(param);
		return this;
	}

	public MovementDTOBuilder withMoveDateAndTime(Date param) {
		movementDTO.setMoveDateAndTime(param);
		return this;
	}

	public MovementDTOBuilder withDueDate(Date param) {
		movementDTO.setDueDate(param);
		return this;
	}

	public MovementDTOBuilder withPreviousValue(double param) {
		movementDTO.setPreviousValue(param);
		return this;
	}

	public MovementDTOBuilder withActivityType(ActivityTypeDTO param) {
		movementDTO.setActivityType(param);
		return this;
	}

	public MovementDTOBuilder withEmployee(EmployeeDTO param) {
		movementDTO.setEmployee(param);
		return this;
	}

	public MovementDTO now() {
		return movementDTO;
	}
}