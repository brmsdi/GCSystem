package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import java.lang.String;
import system.gc.dtos.CondominiumDTO;
import system.gc.dtos.LesseeDTO;
import java.util.Set;
import java.util.Date;
import system.gc.dtos.TypeProblemDTO;
import system.gc.dtos.StatusDTO;
import system.gc.dtos.RepairRequestDTO;

public class RepairRequestDTOBuilder {
	private RepairRequestDTO repairRequestDTO;
	private RepairRequestDTOBuilder(){}

	public static RepairRequestDTOBuilder newInstance() {
		RepairRequestDTOBuilder builder = new RepairRequestDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(RepairRequestDTOBuilder builder) {
		builder.repairRequestDTO = new RepairRequestDTO();
		RepairRequestDTO repairRequestDTO = builder.repairRequestDTO;
		
		repairRequestDTO.setId(0);
		repairRequestDTO.setProblemDescription("");
		repairRequestDTO.setDate(null);
		repairRequestDTO.setTypeProblem(null);
		repairRequestDTO.setLessee(null);
		repairRequestDTO.setCondominium(null);
		repairRequestDTO.setStatus(null);
		repairRequestDTO.setItems(null);
		repairRequestDTO.setApartmentNumber("");
	}

	public RepairRequestDTOBuilder withId(Integer param) {
		repairRequestDTO.setId(param);
		return this;
	}

	public RepairRequestDTOBuilder withProblemDescription(String param) {
		repairRequestDTO.setProblemDescription(param);
		return this;
	}

	public RepairRequestDTOBuilder withDate(Date param) {
		repairRequestDTO.setDate(param);
		return this;
	}

	public RepairRequestDTOBuilder withTypeProblem(TypeProblemDTO param) {
		repairRequestDTO.setTypeProblem(param);
		return this;
	}

	public RepairRequestDTOBuilder withLessee(LesseeDTO param) {
		repairRequestDTO.setLessee(param);
		return this;
	}

	public RepairRequestDTOBuilder withCondominium(CondominiumDTO param) {
		repairRequestDTO.setCondominium(param);
		return this;
	}

	public RepairRequestDTOBuilder withStatus(StatusDTO param) {
		repairRequestDTO.setStatus(param);
		return this;
	}

	public RepairRequestDTOBuilder withItems(Set param) {
		repairRequestDTO.setItems(param);
		return this;
	}

	public RepairRequestDTOBuilder withApartmentNumber(String param) {
		repairRequestDTO.setApartmentNumber(param);
		return this;
	}

	public RepairRequestDTO now() {
		return repairRequestDTO;
	}
}