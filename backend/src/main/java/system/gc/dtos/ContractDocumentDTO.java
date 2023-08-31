package system.gc.dtos;

import lombok.Data;
import system.gc.entities.Contract;
import system.gc.utils.DateUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static system.gc.utils.DateUtils.*;
import static system.gc.utils.NumberUtils.formatCoin;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Data
public class ContractDocumentDTO {
    private Integer id;
    private String contractDate;
    private String contractValue;
    private int monthlyPaymentDate;
    private int monthlyDueDate;
    private String contractExpirationDate;
    private int apartmentNumber;
    private StatusDTO status;
    private CondominiumDTO condominium;
    private LesseeDTO lessee;

    public ContractDocumentDTO(){

    }
    public ContractDocumentDTO(Contract contract) {
        this.setId(contract.getId());
        this.setContractDate(forView(contract.getContractDate()));
        this.setContractExpirationDate(forView(contract.getContractExpirationDate()));
        this.setMonthlyPaymentDate(contract.getMonthlyPaymentDate());
        this.setMonthlyDueDate(contract.getMonthlyDueDate());
        this.setApartmentNumber(contract.getApartmentNumber());
        this.setContractValue(formatCoin(3, 2, contract.getContractValue()));
        LesseeDTO lesseeDTO = new LesseeDTO();
        lesseeDTO.setId(contract.getLessee().getId());
        lesseeDTO.setName(contract.getLessee().getName());
        lesseeDTO.setRg(contract.getLessee().getRg());
        lesseeDTO.setCpf(contract.getLessee().getCpf());
        this.setLessee(lesseeDTO);
        this.setStatus(new StatusDTO().toDTO(contract.getStatus()));
        this.setCondominium(new CondominiumDTO(contract.getCondominium()));
    }
}
