package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import system.gc.entities.Employee;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 */
@Getter
@Setter
@Slf4j
public class EmployeeDTO implements ConvertEntityAndDTO<EmployeeDTO, Employee>, AuthenticateDTO<EmployeeDTO, Employee> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String rg;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String cpf;

    @NotNull(message = "{required.validation}")
    private Date birthDate;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String email;

    @NotNull(message = "{required.validation}")
    private Date hiringDate;

    private String password;

    @NotNull(message = "{required.validation}")
    private RoleDTO role;

    private Set<SpecialtyDTO> specialties;
    private Set<MovementDTO> movements;

    @NotNull(message = "{required.validation}")
    private StatusDTO status;

    public EmployeeDTO() {}

    public EmployeeDTO(String cpf) {
        setCpf(cpf);
    }

    public EmployeeDTO(String name, String rg, String cpf, Date birthDate, String email, Date hiringDate, String password, RoleDTO role, Set<SpecialtyDTO> specialties, Set<MovementDTO> movements, StatusDTO status) {
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

    public EmployeeDTO(Employee employee) {
        setId(employee.getId());
        setName(employee.getName());
        setRg(employee.getRg());
        setCpf(employee.getCpf());
        setBirthDate(employee.getBirthDate());
        setEmail(employee.getEmail());
        setHiringDate(employee.getHiringDate());
        setRole(new RoleDTO(employee.getRole()));
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        setSpecialties(specialtyDTO.convertSetEntityToSetEntityDTO(employee.getSpecialties()));
        //MovementDTO movementDTO = new MovementDTO();
        //setMovements(movementDTO.convertSetEntityToSetEntityDTO(employee.getMovements()));
        setStatus(new StatusDTO(employee.getStatus()));
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
                employeeDTO.getPassword(),
                new RoleDTO().toEntity(employeeDTO.getRole()),
                new SpecialtyDTO().convertSetEntityDTOFromSetEntity(employeeDTO.getSpecialties()),
                new MovementDTO().convertSetEntityDTOFromSetEntity(employeeDTO.getMovements()),
                new StatusDTO().toEntity(employeeDTO.getStatus()));
        if (employeeDTO.getId() != null) {
            employee.setId(employeeDTO.getId());
        }
        return employee;
    }

    /**
     * @return 'DTO' DTO da entidade correspondente ao tipo de autenticação. Instancia com senha para gerenciamento interno do ProviderManager.
     */
    @Override
    public EmployeeDTO initAuthenticate(Employee employee) {
        EmployeeDTO result = new EmployeeDTO();
        result.setId(employee.getId());
        result.setName(employee.getName());
        result.setCpf(employee.getCpf());
        result.setRole(new RoleDTO(employee.getRole()));
        result.setStatus(new StatusDTO(employee.getStatus()));
        result.setPassword(employee.getPassword());
        return result;
    }
}
