package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
public class StatusDTO implements ConvertEntityAndDTO<StatusDTO, Status> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    public StatusDTO() {
    }

    public StatusDTO(String name) {
        setName(name);
    }

    public StatusDTO(Status status) {
        setId(status.getId());
        setName(status.getName());
    }

    @Override
    public StatusDTO toDTO(Status status) {
        return new StatusDTO(status);
    }

    @Override
    public Status toEntity(StatusDTO statusDTO) {
        Status status = new Status(statusDTO.getName());
        if (statusDTO.getId() != null) {
            status.setId(statusDTO.getId());
        }
        return status;
    }
}
