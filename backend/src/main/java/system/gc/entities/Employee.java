package system.gc.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String rg;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String cpf;

    @NotNull
    private Date birthDate;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String email;
    
    @NotNull
    private Date hiringDate;

    @NotNull
    @NotBlank
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_role_id", referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "employee")
    private Set<Movement> movements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_id", referencedColumnName = "id")
    private Status status;

    @OneToMany(mappedBy = "employee")
    private Set<LogChangePassword> logChangePassword;

    @ManyToMany(mappedBy = "employees")
    private Set<OrderService> orderServices;

    public Employee() {}

    public Employee(String name, String rg, String cpf, Date birthDate, String email, Date hiringDate, String password, Role role, Set<Movement> movements, Status status) {
        setName(name);
        setRg(rg);
        setCpf(cpf);
        setBirthDate(birthDate);
        setEmail(email);
        setHiringDate(hiringDate);
        setPassword(password);
        setRole(role);
        setMovements(movements);
        setStatus(status);
    }
}
