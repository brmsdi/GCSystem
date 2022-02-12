package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Specialty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SpecialtyDTO implements ConvertEntityAndDTO<SpecialtyDTO, Specialty> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    public SpecialtyDTO() {
    }

    public SpecialtyDTO(Specialty specialty) {
        setId(specialty.getId());
        setName(specialty.getName());
    }

    @Override
    public SpecialtyDTO toDTO(Specialty specialty) {
        return new SpecialtyDTO(specialty);
    }

    @Override
    public Specialty toEntity(SpecialtyDTO specialtyDTO) {
        Specialty specialty = new Specialty(specialtyDTO.getName());
        if (specialtyDTO.getId() != null) {
            specialty.setId(specialtyDTO.getId());
        }
        return specialty;
    }
}
