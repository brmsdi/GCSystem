package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private String description;
    private int quantity;
    private double value;

    public Item() {}
}
