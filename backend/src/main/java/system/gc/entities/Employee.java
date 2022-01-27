package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	private String name;

	@NotNull
	@NotBlank
	private String rg;

	@NotNull
	@NotBlank
	@Column(unique = true)
	private String cpf;

	private Date birthDate;

	@NotNull
	@NotBlank
	private String email;

	private Date hiringDate;

	@ManyToOne
	@JoinColumn(name = "fk_role_id", referencedColumnName = "id")
	private Role role;

	@ManyToMany
	@JoinTable(
			name = "employee_specialty",
			joinColumns = @JoinColumn(name = "employee_id"),
			inverseJoinColumns = @JoinColumn(name = "specialty_id")
	)
	private Set<Specialty> specialties = new HashSet<>();

	@OneToMany(mappedBy = "employee")
	private Set<Movement> movements = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "fk_status_id", referencedColumnName = "id")
	private Status status;

	public Employee() {}

	public Employee(String name, String rg, String cpf, Date birthDate, String email, Date hiringDate, Role role, Set<Specialty> specialties, Set<Movement> movements, Status status) {
		this.name = name;
		this.rg = rg;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.email = email;
		this.hiringDate = hiringDate;
		this.role = role;
		this.specialties = specialties;
		this.movements = movements;
		this.status = status;
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

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<Specialty> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(Set<Specialty> specialties) {
		this.specialties = specialties;
	}

	public Set<Movement> getMovements() {
		return movements;
	}

	public void setMovements(Set<Movement> movements) {
		this.movements = movements;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
