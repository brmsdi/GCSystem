package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.TypeProblem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
public class TypeProblemDTO implements ConvertEntityAndDTO<TypeProblemDTO, TypeProblem> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    public TypeProblemDTO() {}

    public TypeProblemDTO(String name) {
        setName(name);
    }

    public TypeProblemDTO(TypeProblem typeProblem)
    {
        setId(typeProblem.getId());
        setName(typeProblem.getName());
    }

    @Override
    public TypeProblemDTO toDTO(TypeProblem typeProblem) {
        return new TypeProblemDTO(typeProblem);
    }

    @Override
    public TypeProblem toEntity(TypeProblemDTO typeProblemDTO) {
        TypeProblem typeProblem = new TypeProblem();
        typeProblem.setName(typeProblemDTO.getName());
        if(typeProblemDTO.getId() != null) {
            typeProblem.setId(typeProblemDTO.getId());
        }
        return typeProblem;
    }
}
