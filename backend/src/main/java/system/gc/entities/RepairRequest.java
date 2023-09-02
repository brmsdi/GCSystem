package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_order_service", referencedColumnName = "id")
    private OrderService orderService;

    @OneToMany(mappedBy = "repairRequest", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Item> items = new HashSet<>();

    @NotNull
    @NotBlank
    private String apartmentNumber;

    public RepairRequest() {}

    public void setItems(Set<Item> items) {
        this.items.clear();
        if (items != null) this.items.addAll(items);
    }
}
