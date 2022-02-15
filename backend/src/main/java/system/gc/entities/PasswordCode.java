package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class PasswordCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String code;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_status_id", referencedColumnName = "id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "fk_employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "fk_lessee_id", referencedColumnName = "id")
    private Lessee lessee;

    public PasswordCode() {}

    public PasswordCode(Object entity, String code, Status status) {
        if(entity instanceof Employee) {
            setEmployee((Employee) entity);
        } else if(entity instanceof Lessee) {
            setLessee((Lessee) entity);
        } else {
            throw new IllegalArgumentException("Erro ao criar classe PasswordCode");
        }
        setCode(code);
        setStatus(status);
    }
}
