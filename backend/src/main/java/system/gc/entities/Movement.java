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

	@NotNull
	@ManyToOne
	@JoinColumn(name = "debt_id", referencedColumnName = "id")
	private Debt debt;

	@OneToOne
	@JoinColumn(name = "activityType_id", referencedColumnName = "id")
	private ActivityType activityType;

	@ManyToOne
	@JoinColumn(name = "employee_id", referencedColumnName = "id")
	private Employee employee;

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
