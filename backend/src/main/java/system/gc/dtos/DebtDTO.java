package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Debt;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class DebtDTO implements ConvertEntityAndDTO<DebtDTO, Debt> {

    private Integer id;

    @NotNull(message = "{required.validation}")
    private Date dueDate;

    @NotNull(message = "{required.validation}")
    private double value;

    @NotNull(message = "{required.validation}")
    private StatusDTO status;

    private Set<MovementDTO> movements = new HashSet<>();

    @NotNull(message = "{required.validation}")
    private LesseeDTO lessee;

    public DebtDTO() {
    }

    public DebtDTO(Date dueDate, double value, StatusDTO status, Set<MovementDTO> movements, LesseeDTO lessee) {
        setDueDate(dueDate);
        setValue(value);
        setStatus(status);
        setMovements(movements);
        setLessee(lessee);
    }

    public DebtDTO(Debt debt) {
        setId(debt.getId());
        setDueDate(debt.getDueDate());
        setValue(debt.getValue());
        setStatus(new StatusDTO().toDTO(debt.getStatus()));
        setMovements(new MovementDTO().convertSetEntityToSetEntityDTO(debt.getMovements()));
        setLessee(new LesseeDTO().toDTO(debt.getLessee()));
    }

    public static DebtDTO toViewByLessee(DebtDTO debtDTO) {
        DebtDTO debtView = new DebtDTO();
        debtView.setId(debtDTO.getId());
        debtView.setDueDate(debtDTO.getDueDate());
        debtView.setValue(debtDTO.getValue());
        debtView.setStatus(debtDTO.getStatus());
        debtView.setMovements(debtDTO.getMovements());
        return debtView;
    }

    @Override
    public DebtDTO toDTO(Debt debt) {
        return new DebtDTO(debt);
    }

    @Override
    public Debt toEntity(DebtDTO debtDTO) {
        Debt debt = new Debt(debtDTO.getDueDate(),
                debtDTO.getValue(),
                new StatusDTO().toEntity(debtDTO.getStatus()),
                new MovementDTO().convertSetEntityDTOToSetEntity(debtDTO.getMovements()),
                new LesseeDTO().toEntity(debtDTO.getLessee()));
        if (debtDTO.getId() != null) {
            debt.setId(debtDTO.getId());
        }
        return debt;
    }
}
