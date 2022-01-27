package system.gc.dtos;
import system.gc.entities.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StatusDTO implements ConvertEntityAndDTO<StatusDTO, Status> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    public StatusDTO() {}

    public StatusDTO(String name) {
        setName(name);
    }

    public StatusDTO(Status status) {
        setId(status.getId());
        setName(status.getName());
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
    public StatusDTO toDTO(Status status) {
        return new StatusDTO(status) ;
    }

    @Override
    public Status toEntity(StatusDTO statusDTO) {
        Status status = new Status(statusDTO.getName());
        if(statusDTO.getId() != null) {
            status.setId(statusDTO.getId());
        }
        return status;
    }
}
