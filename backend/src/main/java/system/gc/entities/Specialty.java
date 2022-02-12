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
public class Specialty implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "specialties")
    private Set<Employee> employees = new HashSet<>();

    public Specialty() {
    }

    public Specialty(String name) {
        setName(name);
    }

    public Specialty(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public Specialty(String name, Set<Employee> employees) {
        this.setName(name);
        this.setEmployees(employees);
    }
}
