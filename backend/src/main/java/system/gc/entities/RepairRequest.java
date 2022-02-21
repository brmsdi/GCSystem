package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class RepairRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String problemDescription;

    @NotNull
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_typeProblem_id", referencedColumnName = "id")
    private TypeProblem typeProblem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_lessee_id", referencedColumnName = "id")
    private Lessee lessee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_condominium_id", referencedColumnName = "id")
    private Condominium condominium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_id", referencedColumnName = "id")
    private Status status;

    public RepairRequest() {}
}
