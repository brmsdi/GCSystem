package system.gc.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
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
	@JoinColumn(name = "fk_condominium_id", referencedColumnName = "id")
	private Condominium condominium;

	@ManyToOne
	@JoinColumn(name = "fk_contract_id", referencedColumnName = "id")
	private Lessee lessee;

	public Contract() {}

	public Contract(Date contractDate, double contractValue, Date monthlyPaymentDate, Date monthlyDueDate, Date contractExpirationDate, int apartmentNumber, Condominium condominium, Lessee lessee) {
		setContractDate(contractDate);
		setContractValue(contractValue);
		setMonthlyPaymentDate(monthlyPaymentDate);
		setMonthlyDueDate(monthlyDueDate);
		setContractExpirationDate(contractExpirationDate);
		setApartmentNumber(apartmentNumber);
		setCondominium(condominium);
		setLessee(lessee);
	}
}
