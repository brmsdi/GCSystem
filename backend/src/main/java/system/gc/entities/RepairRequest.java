package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_repair_request", referencedColumnName = "id")
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
