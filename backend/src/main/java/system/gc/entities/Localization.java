package system.gc.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
@Entity
public class Localization implements Serializable {

    @Id
    private int zipCode;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String road;

    public Localization() {
    }

    public Localization(String name, String road, int zipCode) {
        setName(name);
        setRoad(road);
        setZipCode(zipCode);
    }
}
