package system.gc.dtos;
import system.gc.entities.Status;

public class StatusDTO implements ConvertEntityAndDTO<StatusDTO, Status> {
    private Integer id;
    private String name;

    public StatusDTO() {}

    public StatusDTO(String name) {
        this.name = name;
    }

    public StatusDTO(Status status) {
        this.id = status.getId();
        this.name = status.getName();
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
