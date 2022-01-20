package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Contract implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotBlank
	private Date contractDate;

	@NotNull
	@NotBlank
	private double contractValue;

	@NotNull
	@NotBlank
	private Date monthlyPaymentDate;

	@NotNull
	@NotBlank
	private Date monthlyDueDate;

	@NotNull
	@NotBlank
	private Date contractExpirationDate;

	@NotNull
	@NotBlank
	private int apartmentNumber;

	@ManyToOne
	@JoinColumn(name = "condominium_id", referencedColumnName = "id")
	private Condominium condominium;

	@ManyToOne
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	private Lessee lessee;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public double getContractValue() {
		return contractValue;
	}

	public void setContractValue(double contractValue) {
		this.contractValue = contractValue;
	}

	public Date getMonthlyPaymentDate() {
		return monthlyPaymentDate;
	}

	public void setMonthlyPaymentDate(Date monthlyPaymentDate) {
		this.monthlyPaymentDate = monthlyPaymentDate;
	}

	public Date getMonthlyDueDate() {
		return monthlyDueDate;
	}

	public void setMonthlyDueDate(Date monthlyDueDate) {
		this.monthlyDueDate = monthlyDueDate;
	}

	public Date getContractExpirationDate() {
		return contractExpirationDate;
	}

	public void setContractExpirationDate(Date contractExpirationDate) {
		this.contractExpirationDate = contractExpirationDate;
	}

	public int getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public Condominium getCondominium() {
		return condominium;
	}

	public void setCondominium(Condominium condominium) {
		this.condominium = condominium;
	}

	public Lessee getLessee() {
		return lessee;
	}

	public void setLessee(Lessee lessee) {
		this.lessee = lessee;
	}
}
