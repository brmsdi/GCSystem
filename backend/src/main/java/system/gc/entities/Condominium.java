package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Condominium implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	private String name;

	@NotNull
	@NotBlank
	private String description;

	@NotNull
	@NotBlank
	private int numberApartments;

	public Condominium() {}

	public Condominium(String name, String description, int numberApartments, Status status, LocalizationCondominium localizationCondominium, Set<Contract> contract) {
		this.name = name;
		this.description = description;
		this.numberApartments = numberApartments;
		this.status = status;
		this.localizationCondominium = localizationCondominium;
		this.contract = contract;
	}

	@ManyToOne
	@JoinColumn(name = "fk_status_id", referencedColumnName = "id")
	private Status status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_localization_condominium_id", referencedColumnName = "id")
	private LocalizationCondominium localizationCondominium;

	@OneToMany(mappedBy = "condominium")
	private Set<Contract> contract;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberApartments() {
		return numberApartments;
	}

	public void setNumberApartments(int numberApartments) {
		this.numberApartments = numberApartments;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalizationCondominium getLocalizationCondominium() {
		return localizationCondominium;
	}

	public void setLocalizationCondominium(LocalizationCondominium localizationCondominium) {
		this.localizationCondominium = localizationCondominium;
	}

	public Set<Contract> getContract() {
		return contract;
	}

	public void setContract(Set<Contract> contract) {
		this.contract = contract;
	}
}
