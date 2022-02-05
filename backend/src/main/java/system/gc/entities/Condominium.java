package system.gc.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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
	private int numberApartments;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_status_id", referencedColumnName = "id")
	private Status status;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_localization_condominium_id", referencedColumnName = "id")
	private LocalizationCondominium localizationCondominium;

	@OneToMany(mappedBy = "condominium")
	private Set<Contract> contract = new HashSet<>();

	public Condominium() {}

	public Condominium(String name, String description, int numberApartments, Status status, LocalizationCondominium localizationCondominium, Set<Contract> contract) {
		setName(name);
		setDescription(description);
		setNumberApartments(numberApartments);
		setStatus(status);
		setLocalizationCondominium(localizationCondominium);
		setContract(contract);
	}
}
