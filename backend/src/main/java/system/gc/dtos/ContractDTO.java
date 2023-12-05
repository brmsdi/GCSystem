package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Contract;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
public class ContractDTO implements ConvertEntityAndDTO<ContractDTO, Contract> {

    private Integer id;

    @NotNull(message = "{required.validation}")
    private Date contractDate;

    @NotNull(message = "{required.validation}")
    private double contractValue;

    @NotNull(message = "{required.validation}")
    private int monthlyPaymentDate;

    @NotNull(message = "{required.validation}")
    private int monthlyDueDate;

    @NotNull(message = "{required.validation}")
    private Date contractExpirationDate;

    @NotNull(message = "{required.validation}")
    private int apartmentNumber;

    @NotNull(message = "{required.validation}")
    private StatusDTO status;

    @NotNull(message = "{required.validation}")
    private CondominiumDTO condominium;

    @NotNull(message = "{required.validation}")
    private LesseeDTO lessee;

    public ContractDTO() {
    }

    public ContractDTO(Date contractDate, double contractValue, int monthlyPaymentDate, int monthlyDueDate, Date contractExpirationDate, int apartmentNumber, StatusDTO status, CondominiumDTO condominium, LesseeDTO lessee) {
        setContractDate(contractDate);
        setContractValue(contractValue);
        setMonthlyPaymentDate(monthlyPaymentDate);
        setMonthlyDueDate(monthlyDueDate);
        setContractExpirationDate(contractExpirationDate);
        setApartmentNumber(apartmentNumber);
        setStatus(status);
        setCondominium(condominium);
        setLessee(lessee);
    }

    public ContractDTO(Contract contract) {
        setId(contract.getId());
        setContractDate(contract.getContractDate());
        setContractValue(contract.getContractValue());
        setMonthlyPaymentDate(contract.getMonthlyPaymentDate());
        setMonthlyDueDate(contract.getMonthlyDueDate());
        setContractExpirationDate(contract.getContractExpirationDate());
        setApartmentNumber(contract.getApartmentNumber());
        setStatus(new StatusDTO().toDTO(contract.getStatus()));
        setCondominium(new CondominiumDTO().toDTO(contract.getCondominium()));
        setLessee(new LesseeDTO().toDTO(contract.getLessee()));
    }

    public ContractDTO(String cpf) {
        setLessee(new LesseeDTO(cpf));
    }

    @Override
    public ContractDTO toDTO(Contract contract) {
        return new ContractDTO(contract);
    }

    @Override
    public Contract toEntity(ContractDTO contractDTO) {
        Contract contract = new Contract(contractDTO.getContractDate(),
                contractDTO.getContractValue(),
                contractDTO.getMonthlyPaymentDate(),
                contractDTO.getMonthlyDueDate(),
                contractDTO.getContractExpirationDate(),
                contractDTO.getApartmentNumber(),
                new StatusDTO().toEntity(contractDTO.getStatus()),
                new CondominiumDTO().toEntity(contractDTO.getCondominium()),
                new LesseeDTO().toEntity(contractDTO.getLessee()));
        if (contractDTO.getId() != null) {
            contract.setId(contractDTO.getId());
        }
        return contract;
    }

    public static ContractDTO forListViewMobile(Contract contract) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setId(contract.getId());
        contractDTO.setContractDate(contract.getContractDate());
        contractDTO.setContractExpirationDate(contract.getContractExpirationDate());
        contractDTO.setMonthlyPaymentDate(contract.getMonthlyPaymentDate());
        contractDTO.setMonthlyDueDate(contract.getMonthlyDueDate());
        contractDTO.setApartmentNumber(contract.getApartmentNumber());
        contractDTO.setContractValue(contract.getContractValue());
        contractDTO.setLessee(new LesseeDTO());
        contractDTO.getLessee().setId(contract.getLessee().getId());
        contractDTO.getLessee().setName(contract.getLessee().getName());
        contractDTO.setStatus(new StatusDTO().toDTO(contract.getStatus()));
        return contractDTO;
    }
}