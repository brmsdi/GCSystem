package system.gc.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
@Entity
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Date contractDate;

    @NotNull
    private double contractValue;

    @NotNull
    private int monthlyPaymentDate;

    @NotNull
    private int monthlyDueDate;

    @NotNull
    private Date contractExpirationDate;

    @NotNull
    private int apartmentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_id", referencedColumnName = "id")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_condominium_id", referencedColumnName = "id")
    private Condominium condominium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_lessee_id", referencedColumnName = "id")
    private Lessee lessee;

    public Contract() {
    }

    public Contract(Date contractDate, double contractValue, int monthlyPaymentDate, int monthlyDueDate, Date contractExpirationDate, int apartmentNumber, Status status, Condominium condominium, Lessee lessee) {
        setContractDate(contractDate);
        setContractValue(contractValue);
        setMonthlyPaymentDate(monthlyPaymentDate);
        setMonthlyDueDate(monthlyDueDate);
        setContractExpirationDate(contractExpirationDate);
        setApartmentNumber(apartmentNumber);
        setStatus(status);
        setCondominium(condominium);
        setLessee(lessee);
    }
}
