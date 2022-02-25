package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String description;
    private int quantity;
    private double value;

    @ManyToOne
    @JoinColumn(name = "fk_repair_request", referencedColumnName = "id")
    private RepairRequest repairRequest;

    public Item() {}
}
