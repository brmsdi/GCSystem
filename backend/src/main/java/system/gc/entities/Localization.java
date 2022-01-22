package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Localization implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	private String name;

	@NotNull
	@NotBlank
	private String road;

	@NotNull
	@NotBlank
	private String zipCode;

	@OneToMany(mappedBy = "localization")
	private Set<LocalizationCondominium> localizationCondominiums = new HashSet<>();

	public Localization() {}

	public Localization(String name, String road, String zipCode, Set<LocalizationCondominium> localizationCondominiums) {
		this.name = name;
		this.road = road;
		this.zipCode = zipCode;
		this.localizationCondominiums = localizationCondominiums;
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

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Set<LocalizationCondominium> getLocalizationCondominiums() {
		return localizationCondominiums;
	}

	public void setLocalizationCondominiums(Set<LocalizationCondominium> localizationCondominiums) {
		this.localizationCondominiums = localizationCondominiums;
	}
}
