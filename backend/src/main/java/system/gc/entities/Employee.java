package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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

    private Date birthDate;

    @NotNull
    @NotBlank
    private String email;

    private Date hiringDate;

    @NotNull
    @NotBlank
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_role_id", referencedColumnName = "id")
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "employee_specialty",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set<Specialty> specialties = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<Movement> movements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_id", referencedColumnName = "id")
    private Status status;

    @OneToMany(mappedBy = "employee")
    private Set<PasswordCode> passwordCode = new HashSet<>();

    public Employee() {
    }

    public Employee(String name, String rg, String cpf, Date birthDate, String email, Date hiringDate, String password, Role role, Set<Specialty> specialties, Set<Movement> movements, Status status) {
        setName(name);
        setRg(rg);
        setCpf(cpf);
        setBirthDate(birthDate);
        setEmail(email);
        setHiringDate(hiringDate);
        setPassword(password);
        setRole(role);
        setSpecialties(specialties);
        setMovements(movements);
        setStatus(status);
    }
}
