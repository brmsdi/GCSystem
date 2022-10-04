package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import system.gc.dtos.LesseeDTO;
import java.util.Set;
import java.util.Date;
import system.gc.dtos.StatusDTO;
import system.gc.dtos.DebtDTO;

public class DebtDTOBuilder {
	private DebtDTO debtDTO;
	private DebtDTOBuilder(){}

	public static DebtDTOBuilder newInstance() {
		DebtDTOBuilder builder = new DebtDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(DebtDTOBuilder builder) {
		builder.debtDTO = new DebtDTO();
		DebtDTO debtDTO = builder.debtDTO;
		
		debtDTO.setId(0);
		debtDTO.setDueDate(null);
		debtDTO.setValue(0.0);
		debtDTO.setStatus(null);
		debtDTO.setMovements(null);
		debtDTO.setLessee(null);
	}

	public DebtDTOBuilder withId(Integer param) {
		debtDTO.setId(param);
		return this;
	}

	public DebtDTOBuilder withDueDate(Date param) {
		debtDTO.setDueDate(param);
		return this;
	}

	public DebtDTOBuilder withValue(double param) {
		debtDTO.setValue(param);
		return this;
	}

	public DebtDTOBuilder withStatus(StatusDTO param) {
		debtDTO.setStatus(param);
		return this;
	}

	public DebtDTOBuilder withMovements(Set param) {
		debtDTO.setMovements(param);
		return this;
	}

	public DebtDTOBuilder withLessee(LesseeDTO param) {
		debtDTO.setLessee(param);
		return this;
	}

	public DebtDTO now() {
		return debtDTO;
	}
}