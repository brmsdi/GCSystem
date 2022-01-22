package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Status implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	private String name;

	@OneToMany(mappedBy = "status")
	private Set<Employee> employees = new HashSet<>();

	@OneToMany(mappedBy = "status")
	private Set<Lessee> lessees =  new HashSet<>();

	@OneToMany(mappedBy = "status")
	private Set<Condominium> condominiums = new HashSet<>();

	@OneToMany(mappedBy = "status")
	private Set<Debt> debts  = new HashSet<>();

	public Status() {}

	public Status(String name) {
		this.name = name;
	}

	public Status(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public Status(String name, Set<Employee> employees, Set<Lessee> lessees, Set<Condominium> condominiums, Set<Debt> debts) {
		this.setName(name);
		this.setEmployees(employees);
		this.setLessees(lessees);
		this.setCondominiums(condominiums);
		this.setDebts(debts);
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

	public Set<Lessee> getLessees() {
		return lessees;
	}

	public void setLessees(Set<Lessee> lessees) {
		this.lessees = lessees;
	}

	public Set<Condominium> getCondominiums() {
		return condominiums;
	}

	public void setCondominiums(Set<Condominium> condominiums) {
		this.condominiums = condominiums;
	}

	public Set<Debt> getDebts() {
		return debts;
	}

	public void setDebts(Set<Debt> debts) {
		this.debts = debts;
	}
}
