package system.gc.entities;

import javax.persistence.ManyToMany;
import java.util.Set;

public class Specialty {

	private Integer id;

	private String name;

	@ManyToMany(mappedBy = "specialties")
	private Set<Employee> employees;

	public void setID(Integer id) {

	}

	public Integer getID() {
		return null;
	}

	public void setName(String name) {

	}

	public String getName() {
		return null;
	}

}
