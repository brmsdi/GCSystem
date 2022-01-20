package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Debt implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	private Date dueDate;

	@NotNull
	@NotBlank
	private double value;

	@ManyToOne
	@JoinColumn(name = "status_id", referencedColumnName = "id")
	private Status status;

	@OneToMany(mappedBy = "debt")
	private Set<Movement> movements = new HashSet<>();

	@ManyToOne
	@JoinColumn(name= "lessee_id", referencedColumnName = "id")
	private Lessee lessee;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<Movement> getMovements() {
		return movements;
	}

	public void setMovements(Set<Movement> movements) {
		this.movements = movements;
	}

	public Lessee getLessee() {
		return lessee;
	}

	public void setLessee(Lessee lessee) {
		this.lessee = lessee;
	}
}
