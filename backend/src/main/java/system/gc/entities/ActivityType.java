package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ActivityType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	@NotNull
	private String name;

	@OneToMany(mappedBy = "activityType")
	private Set<Movement> movements = new HashSet<>();

	public ActivityType() {}

	public ActivityType(String name, Set<Movement> movements) {
		this.name = name;
		this.movements = movements;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Movement> getMovements() {
		return movements;
	}

	public void setMovements(Set<Movement> movements) {
		this.movements = movements;
	}
}
