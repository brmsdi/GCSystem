package system.gc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//  @PrimaryKeyJoinColumn
@Entity
public class LocalizationCondominium implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String number;

    @ManyToOne
    @JoinColumn(name = "fk_localization_id", referencedColumnName = "id")
    private Localization localization;

    @OneToOne(mappedBy = "localizationCondominium", cascade = CascadeType.ALL)
    private Condominium condominium;

    public LocalizationCondominium() {}

    public LocalizationCondominium(String number, Localization localization) {
        this.number = number;
        this.localization = localization;
    }

    public LocalizationCondominium(String number, Localization localization, Condominium condominium) {
        this.number = number;
        this.localization = localization;
        this.condominium = condominium;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public Condominium getCondominium() {
        return condominium;
    }

    public void setCondominium(Condominium condominium) {
        this.condominium = condominium;
    }
}
