package system.gc.dtos;

import system.gc.entities.ActivityType;
import system.gc.entities.Debt;
import system.gc.entities.Employee;
import system.gc.entities.Movement;
import java.util.Date;

public class MovementDTO implements ConvertEntityAndDTO<MovementDTO, Movement> {
    private Integer id;
    private Date moveDateAndTime;
    private Date dueDate;
    private double previousValue;
   //private Debt debt;
    //private ActivityType activityType;
    //private Employee employee;

    public MovementDTO() {}

    public MovementDTO(Date moveDateAndTime, Date dueDate, double previousValue, Debt debt, ActivityType activityType, Employee employee) {
        this.moveDateAndTime = moveDateAndTime;
        this.dueDate = dueDate;
        this.previousValue = previousValue;
       // this.debt = debt;
        //this.activityType = activityType;
        //this.employee = employee;
    }

    public MovementDTO(Movement movement) {
        this.id = movement.getId();
        this.moveDateAndTime = movement.getMoveDateAndTime();
        this.dueDate = movement.getDueDate();
        this.previousValue = movement.getPreviousValue();
        //this.debt = movement.getDebt();
        //this.activityType = movement.getActivityType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMoveDateAndTime() {
        return moveDateAndTime;
    }

    public void setMoveDateAndTime(Date moveDateAndTime) {
        this.moveDateAndTime = moveDateAndTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(double previousValue) {
        this.previousValue = previousValue;
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

    /*
    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    } */
}
