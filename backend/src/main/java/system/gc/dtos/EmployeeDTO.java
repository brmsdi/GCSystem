package system.gc.dtos;
import lombok.extern.slf4j.Slf4j;
import system.gc.entities.Employee;
import system.gc.entities.Movement;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * */

@Slf4j
public class EmployeeDTO  implements ConvertEntityAndDTO<EmployeeDTO, Employee> {
    private Integer id;
    private String name;
    private String rg;
    private String cpf;
    private Date birthDate;
    private String email;
    private Date hiringDate;
    private RoleDTO role;
    private Set<SpecialtyDTO> specialties = new HashSet<>();
    private Set<MovementDTO> movements = new HashSet<>();
    private StatusDTO status;

    public EmployeeDTO() {}

    public EmployeeDTO(String name, String rg, String cpf, Date birthDate, String email, Date hiringDate, RoleDTO role, Set<SpecialtyDTO> specialties, Set<MovementDTO> movements, StatusDTO status) {
        this.name = name;
        this.rg = rg;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.email = email;
        this.hiringDate = hiringDate;
        this.role = role;
        this.specialties = specialties;
        this.movements = movements;
        this.status = status;
    }

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.rg = employee.getRg();
        this.cpf = employee.getCpf();
        this.birthDate = employee.getBirthDate();
        this.email = employee.getEmail();
        this.hiringDate = employee.getHiringDate();
        this.role = new RoleDTO(employee.getRole());
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        this.specialties = specialtyDTO.convertSetEntityToSetEntityDTO(employee.getSpecialties());
        MovementDTO movementDTO = new MovementDTO();
        this.movements = movementDTO.convertSetEntityToSetEntityDTO(employee.getMovements());
        this.status = new StatusDTO(employee.getStatus());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public Set<SpecialtyDTO> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<SpecialtyDTO> specialties) {
        this.specialties = specialties;
    }

    public Set<MovementDTO> getMovements() {
        return movements;
    }

    public void setMovements(Set<MovementDTO> movements) {
        this.movements = movements;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    @Override
    public EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(employee);
    }

    @Override
    public Employee toEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getName(),
                employeeDTO.getRg(),
                employeeDTO.getCpf(),
                employeeDTO.getBirthDate(),
                employeeDTO.getEmail(),
                employeeDTO.getHiringDate(),
                new RoleDTO().toEntity(employeeDTO.getRole()),
                new SpecialtyDTO().convertSetEntityDTOFromSetEntity(employeeDTO.getSpecialties()),
                new MovementDTO().convertSetEntityDTOFromSetEntity(employeeDTO.getMovements()),
                new StatusDTO().toEntity(employeeDTO.getStatus()));
        if(employeeDTO.getId() != null) {
            employee.setId(employeeDTO.getId());
        }
        return employee;
    }
}
