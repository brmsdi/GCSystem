package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Specialty implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	private String name;

	@ManyToMany(mappedBy = "specialties")
	private Set<Employee> employees = new HashSet<>();

	public Specialty() {}

	public Specialty(String name) {
		this.name = name;
	}

	public Specialty(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public Specialty(String name, Set<Employee> employees) {
		this.setName(name);
		this.setEmployees(employees);
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

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
}
