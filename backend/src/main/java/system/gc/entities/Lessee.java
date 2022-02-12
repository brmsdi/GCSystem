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
public class Lessee implements Serializable {

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
    private String email;

    @NotNull
    @NotBlank
    private String contactNumber;

    @NotNull
    @NotBlank
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_id", referencedColumnName = "id")
    private Status status;

    @OneToMany(mappedBy = "lessee")
    private Set<Debt> debts = new HashSet<>();

    @OneToMany(mappedBy = "lessee")
    private Set<Contract> contracts = new HashSet<>();

    public Lessee() {
    }

    public Lessee(String name, String rg, String cpf, Date birthDate, String email, String contactNumber, String password, Status status, Set<Debt> debts, Set<Contract> contracts) {
        setName(name);
        setRg(rg);
        setCpf(cpf);
        setBirthDate(birthDate);
        setEmail(email);
        setContactNumber(contactNumber);
        setPassword(password);
        setStatus(status);
        setDebts(debts);
        setContracts(contracts);
    }

}
