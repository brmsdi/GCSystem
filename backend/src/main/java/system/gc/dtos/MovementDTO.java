package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.ActivityType;
import system.gc.entities.Debt;
import system.gc.entities.Employee;
import system.gc.entities.Movement;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class MovementDTO implements ConvertEntityAndDTO<MovementDTO, Movement> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    private Date moveDateAndTime;

    @NotNull(message = "{required.validation}")
    private Date dueDate;

    @NotNull(message = "{required.validation}")
    private double previousValue;

    @NotNull(message = "{required.validation}")
    private DebtDTO debt;

    @NotNull(message = "{required.validation}")
    private ActivityTypeDTO activityType;

    @NotNull(message = "{required.validation}")
    private EmployeeDTO employee;

    public MovementDTO() {}

    public MovementDTO(Date moveDateAndTime, Date dueDate, double previousValue, DebtDTO debt, ActivityTypeDTO activityType, EmployeeDTO employee) {
        setMoveDateAndTime(moveDateAndTime);
        setDueDate(dueDate);
        setPreviousValue(previousValue);
        setDebt(debt);
        setActivityType(activityType);
        setEmployee(employee);
    }

    public MovementDTO(Movement movement) {
        setId(movement.getId());
        setMoveDateAndTime(movement.getMoveDateAndTime());
        setDueDate(movement.getDueDate());
        setPreviousValue(movement.getPreviousValue());
        setEmployee(new EmployeeDTO().toDTO(movement.getEmployee()));
        setActivityType(new ActivityTypeDTO().toDTO(movement.getActivityType()));
    }

    @Override
    public MovementDTO toDTO(Movement movement) {
        return new MovementDTO(movement);
    }

    @Override
    public Movement toEntity(MovementDTO movementDTO) {
        Movement movement = new Movement(movementDTO.getMoveDateAndTime(),
                movementDTO.getDueDate(),
                movementDTO.getPreviousValue(),
                new DebtDTO().toEntity(movementDTO.getDebt()),
                new ActivityTypeDTO().toEntity(movementDTO.getActivityType()),
                new EmployeeDTO().toEntity(movementDTO.getEmployee()));
        if(movementDTO.getId() != null) {
            movement.setId(movementDTO.getId());
        }
        return movement;
    }
}
