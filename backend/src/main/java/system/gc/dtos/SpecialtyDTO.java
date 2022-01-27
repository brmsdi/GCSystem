package system.gc.dtos;
import system.gc.entities.Specialty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SpecialtyDTO implements ConvertEntityAndDTO<SpecialtyDTO, Specialty> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    public SpecialtyDTO() {}

    public SpecialtyDTO(Specialty specialty) {
        setId(specialty.getId());
        setName(specialty.getName());
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

    @Override
    public SpecialtyDTO toDTO(Specialty specialty) {
        return new SpecialtyDTO(specialty);
    }

    @Override
    public Specialty toEntity(SpecialtyDTO specialtyDTO) {
        Specialty specialty = new Specialty(specialtyDTO.getName());
        if(specialtyDTO.getId() != null) {
            specialty.setId(specialtyDTO.getId());
        }
        return specialty;
    }
}
