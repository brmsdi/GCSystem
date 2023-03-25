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
public class LocalizationCondominium implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_localization_zipcode", referencedColumnName = "zipCode")
    private Localization localization;

    //@OneToOne(mappedBy = "localizationCondominium")
    //private Condominium condominium;

    public LocalizationCondominium() {
    }

    public LocalizationCondominium(String number, Localization localization) {
        setNumber(number);
        setLocalization(localization);
    }
}
