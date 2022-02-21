package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
public class LogChangePassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String code;

    @NotNull
    private Date date;

    @NotNull
    private short numberOfAttempts;

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

    public LogChangePassword() {}

    public LogChangePassword(Object entity, String code, Status status, Date date, short numberOfAttempts) {
        if(entity instanceof Employee) {
            setEmployee((Employee) entity);
        } else if(entity instanceof Lessee) {
            setLessee((Lessee) entity);
        } else {
            throw new IllegalArgumentException("Erro ao criar classe PasswordCode");
        }
        setCode(code);
        setStatus(status);
        setDate(date);
        setNumberOfAttempts(numberOfAttempts);
    }
}
