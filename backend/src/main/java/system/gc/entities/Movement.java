package system.gc.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
public class Movement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Date moveDateAndTime;

    @NotNull
    private Date dueDate;

    @NotNull
    private double previousValue;

    @ManyToOne
    @JoinColumn(name = "fk_debt_id", referencedColumnName = "id")
    private Debt debt;

    @ManyToOne
    @JoinColumn(name = "fk_activityType_id", referencedColumnName = "id")
    private ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "fk_employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    public Movement() {
    }

    public Movement(Date moveDateAndTime, Date dueDate, double previousValue, Debt debt, ActivityType activityType, Employee employee) {
        setMoveDateAndTime(moveDateAndTime);
        setDueDate(dueDate);
        setPreviousValue(previousValue);
        setDebt(debt);
        setActivityType(activityType);
        setEmployee(employee);
    }

    public Movement(Date moveDateAndTime, Date dueDate, double previousValue, ActivityType activityType, Employee employee) {
        setMoveDateAndTime(moveDateAndTime);
        setDueDate(dueDate);
        setPreviousValue(previousValue);
        setActivityType(activityType);
        setEmployee(employee);
    }
}
