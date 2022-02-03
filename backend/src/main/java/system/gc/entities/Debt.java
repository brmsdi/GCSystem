package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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
	@JoinColumn(name = "fk_status_id", referencedColumnName = "id")
	private Status status;

	@OneToMany(mappedBy = "debt")
	private Set<Movement> movements = new HashSet<>();

	@ManyToOne
	@JoinColumn(name= "fk_lessee_id", referencedColumnName = "id")
	private Lessee lessee;

	public Debt() {}

	public Debt(Date dueDate, double value, Status status, Set<Movement> movements, Lessee lessee) {
		setDueDate(dueDate);
		setValue(value);
		setStatus(status);
		setMovements(movements);
		setLessee(lessee);
	}
}
