package system.gc.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Deprecated
@Getter
@Setter
@Entity
public class Debt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Date dueDate;

    @NotNull
    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_id", referencedColumnName = "id")
    private Status status;

    @OneToMany(mappedBy = "debt")
    private Set<Movement> movements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_lessee_id", referencedColumnName = "id")
    private Lessee lessee;

    public Debt() {
    }

    public Debt(Date dueDate, double value, Status status, Set<Movement> movements, Lessee lessee) {
        setDueDate(dueDate);
        setValue(value);
        setStatus(status);
        setMovements(movements);
        setLessee(lessee);
    }
}
