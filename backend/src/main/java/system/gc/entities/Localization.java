package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
