package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Movement implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	private Date moveDateAndTime;

	@NotNull
	@NotBlank
	private Date dueDate;

	@NotNull
	@NotBlank
	private double previousValue;

	@ManyToOne
	@JoinColumn(name = "fk_debt_id", referencedColumnName = "id")
	private Debt debt;

	@ManyToOne
	@JoinColumn(name = "fk_activityType_id", referencedColumnName = "id")
	private ActivityType activityType;

	@ManyToOne
	@JoinColumn(name = "fk_employee_id", referencedColumnName = "id")
	private Employee employee;

	public Movement() {}

	public Movement(Date moveDateAndTime, Date dueDate, double previousValue) {
		this.moveDateAndTime = moveDateAndTime;
		this.dueDate = dueDate;
		this.previousValue = previousValue;
	}

	public Movement(Date moveDateAndTime, Date dueDate, double previousValue, Debt debt, ActivityType activityType, Employee employee) {
		this.moveDateAndTime = moveDateAndTime;
		this.dueDate = dueDate;
		this.previousValue = previousValue;
		this.debt = debt;
		this.activityType = activityType;
		this.employee = employee;
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
	}
}
