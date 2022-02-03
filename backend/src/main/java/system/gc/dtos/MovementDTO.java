package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.ActivityType;
import system.gc.entities.Debt;
import system.gc.entities.Employee;
import system.gc.entities.Movement;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class MovementDTO implements ConvertEntityAndDTO<MovementDTO, Movement> {
    private Integer id;
    private Date moveDateAndTime;
    private Date dueDate;

    @NotNull
    private double previousValue;
   //private Debt debt;
    //private ActivityType activityType;
    //private Employee employee;

    public MovementDTO() {}

    public MovementDTO(Date moveDateAndTime, Date dueDate, double previousValue, Debt debt, ActivityType activityType, Employee employee) {
        setMoveDateAndTime(moveDateAndTime);
        setDueDate(dueDate);
        setPreviousValue(previousValue);
       // this.debt = debt;
        //this.activityType = activityType;
        //this.employee = employee;
    }

    public MovementDTO(Movement movement) {
        setId(movement.getId());
        setMoveDateAndTime(movement.getMoveDateAndTime());
        setDueDate(movement.getDueDate());
        setPreviousValue(movement.getPreviousValue());
        //this.debt = movement.getDebt();
        //this.activityType = movement.getActivityType();
    }

    @Override
    public MovementDTO toDTO(Movement movement) {
        return new MovementDTO(movement);
    }

    @Override
    public Movement toEntity(MovementDTO movementDTO) {
        Movement movement = new Movement(movementDTO.getMoveDateAndTime(),
                movementDTO.getDueDate(),
                movementDTO.getPreviousValue());
        if(movementDTO.getId() != null) {
            movement.setId(movementDTO.getId());
        }
        return movement;
    }
}
