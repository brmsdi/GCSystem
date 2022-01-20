package system.gc.entities;

import javax.persistence.OneToOne;

public class Localization {

	private Integer id;

	private String name;

	private String road;

	private int number;

	private String zipCode;

	@OneToOne(mappedBy = "localization")
	private Condominium condominium;

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

	public void setRoad(String road) {

	}

	public String getRoad() {
		return null;
	}

	public void setNumber(int number) {

	}

	public int getNumber() {
		return 0;
	}

	public void setZipCode(String code) {

	}

	public String getZipCode() {
		return null;
	}

}
