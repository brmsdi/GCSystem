package system.gc.entities;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Set;

public class Status implements Serializable {

	private Integer id;

	private String name;

	@OneToMany(mappedBy = "status")
	private Set<Employee> employees;

	@OneToMany(mappedBy = "status")
	private Lessee lessee;

	@OneToMany(mappedBy = "status")
	private Condominium condominium;

	@OneToMany(mappedBy = "status")
	private Debt debt;


}
