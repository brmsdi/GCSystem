package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Contract;
import system.gc.entities.Debt;
import system.gc.entities.Lessee;
import system.gc.entities.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class LesseeDTO implements ConvertEntityAndDTO<LesseeDTO, Lessee> {
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
    @NotBlank(message = "{required.validation}")
    private String contactNumber;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String password;

    @NotNull(message = "{required.validation}")
    private StatusDTO status;

    //private Set<Debt> debts = new HashSet<>();

    //private Set<Contract> contracts = new HashSet<>();

    public LesseeDTO() {}

    public LesseeDTO(String cpf) {
        setCpf(cpf);
    }

    public LesseeDTO(String name, String rg, String cpf, Date birthDate, String email, String contactNumber, String password, StatusDTO status) {
        setName(name);
        setRg(rg);
        setCpf(cpf);
        setBirthDate(birthDate);
        setEmail(email);
        setContactNumber(contactNumber);
        setPassword(password);
        setStatus(status);
    }

    public LesseeDTO(Lessee lessee) {
        setId(lessee.getId());
        setName(lessee.getName());
        setRg(lessee.getRg());
        setCpf(lessee.getCpf());
        setBirthDate(lessee.getBirthDate());
        setEmail(lessee.getEmail());
        setContactNumber(lessee.getContactNumber());
        setPassword(lessee.getPassword());
        setStatus(new StatusDTO().toDTO(lessee.getStatus()));
    }

    @Override
    public LesseeDTO toDTO(Lessee lessee) {
        return new LesseeDTO(lessee);
    }

    @Override
    public Lessee toEntity(LesseeDTO lesseeDTO) {
        Lessee lessee = new Lessee(lesseeDTO.getName(),
                lesseeDTO.getRg(),
                lesseeDTO.getCpf(),
                lesseeDTO.getBirthDate(),
                lesseeDTO.getEmail(),
                lesseeDTO.getContactNumber(),
                lesseeDTO.getPassword(),
                new StatusDTO().toEntity(lesseeDTO.getStatus()),
                null,
                null);
        if(lesseeDTO.getId() != null) {
            lessee.setId(lesseeDTO.getId());
        }
        return lessee;
    }
}
