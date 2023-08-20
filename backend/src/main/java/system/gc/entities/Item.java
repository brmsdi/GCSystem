package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotNull
    @NotBlank
    private String description;
    private int quantity;
    private double value;

    @ManyToOne
    @JoinColumn(name = "fk_repair_request", referencedColumnName = "id")
    private RepairRequest repairRequest;

    public Item() {}
}
