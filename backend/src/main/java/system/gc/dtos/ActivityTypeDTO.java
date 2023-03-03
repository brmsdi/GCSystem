package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.ActivityType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Deprecated
@Getter
@Setter
public class ActivityTypeDTO implements ConvertEntityAndDTO<ActivityTypeDTO, ActivityType> {

    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    public ActivityTypeDTO() {
    }

    public ActivityTypeDTO(String name) {
        setName(name);
    }

    public ActivityTypeDTO(ActivityType activityType) {
        setId(activityType.getId());
        setName(activityType.getName());
    }

    @Override
    public ActivityTypeDTO toDTO(ActivityType activityType) {
        return new ActivityTypeDTO(activityType);
    }

    @Override
    public ActivityType toEntity(ActivityTypeDTO activityTypeDTO) {
        ActivityType activityType = new ActivityType(activityTypeDTO.getName());
        if (activityTypeDTO.getId() != null) {
            activityType.setId(activityTypeDTO.getId());
        }
        return activityType;
    }
}
