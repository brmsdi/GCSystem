package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Lessee implements Serializable {

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
	private String cpf;

	@NotNull
	@NotBlank
	private Date birthDate;

	@NotNull
	@NotBlank
	private String email;

	@NotNull
	@NotBlank
	private String contactNumber;

	@ManyToOne
	@JoinColumn(name = "fk_status_id", referencedColumnName = "id")
	private Status status;

	@OneToMany(mappedBy = "lessee")
	private Set<Debt> debts = new HashSet<>();

	@OneToMany(mappedBy = "lessee")
	private Set<Contract> contracts = new HashSet<>();

	public Lessee() {}

	public Lessee(String name, String rg, String cpf, Date birthDate, String email, String contactNumber, Status status, Set<Debt> debts, Set<Contract> contracts) {
		this.name = name;
		this.rg = rg;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.email = email;
		this.contactNumber = contactNumber;
		this.status = status;
		this.debts = debts;
		this.contracts = contracts;
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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<Debt> getDebts() {
		return debts;
	}

	public void setDebts(Set<Debt> debts) {
		this.debts = debts;
	}

	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}
}
