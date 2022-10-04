package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import system.gc.dtos.CondominiumDTO;
import system.gc.dtos.LesseeDTO;
import java.util.Date;
import system.gc.dtos.StatusDTO;
import system.gc.dtos.ContractDTO;

public class ContractDTOBuilder {
	private ContractDTO contractDTO;
	private ContractDTOBuilder(){}

	public static ContractDTOBuilder newInstance() {
		ContractDTOBuilder builder = new ContractDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(ContractDTOBuilder builder) {
		builder.contractDTO = new ContractDTO();
		ContractDTO contractDTO = builder.contractDTO;
		
		contractDTO.setId(0);
		contractDTO.setContractDate(null);
		contractDTO.setContractValue(0.0);
		contractDTO.setMonthlyPaymentDate(0);
		contractDTO.setMonthlyDueDate(0);
		contractDTO.setContractExpirationDate(null);
		contractDTO.setApartmentNumber(0);
		contractDTO.setStatus(null);
		contractDTO.setCondominium(null);
		contractDTO.setLessee(null);
	}

	public ContractDTOBuilder withId(Integer param) {
		contractDTO.setId(param);
		return this;
	}

	public ContractDTOBuilder withContractDate(Date param) {
		contractDTO.setContractDate(param);
		return this;
	}

	public ContractDTOBuilder withContractValue(double param) {
		contractDTO.setContractValue(param);
		return this;
	}

	public ContractDTOBuilder withMonthlyPaymentDate(int param) {
		contractDTO.setMonthlyPaymentDate(param);
		return this;
	}

	public ContractDTOBuilder withMonthlyDueDate(int param) {
		contractDTO.setMonthlyDueDate(param);
		return this;
	}

	public ContractDTOBuilder withContractExpirationDate(Date param) {
		contractDTO.setContractExpirationDate(param);
		return this;
	}

	public ContractDTOBuilder withApartmentNumber(int param) {
		contractDTO.setApartmentNumber(param);
		return this;
	}

	public ContractDTOBuilder withStatus(StatusDTO param) {
		contractDTO.setStatus(param);
		return this;
	}

	public ContractDTOBuilder withCondominium(CondominiumDTO param) {
		contractDTO.setCondominium(param);
		return this;
	}

	public ContractDTOBuilder withLessee(LesseeDTO param) {
		contractDTO.setLessee(param);
		return this;
	}

	public ContractDTO now() {
		return contractDTO;
	}
}