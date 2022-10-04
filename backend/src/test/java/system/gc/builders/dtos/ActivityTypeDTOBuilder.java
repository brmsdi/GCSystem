package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import java.lang.String;
import system.gc.dtos.ActivityTypeDTO;

public class ActivityTypeDTOBuilder {
	private ActivityTypeDTO activityTypeDTO;
	private ActivityTypeDTOBuilder(){}

	public static ActivityTypeDTOBuilder newInstance() {
		ActivityTypeDTOBuilder builder = new ActivityTypeDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(ActivityTypeDTOBuilder builder) {
		builder.activityTypeDTO = new ActivityTypeDTO();
		ActivityTypeDTO activityTypeDTO = builder.activityTypeDTO;
		
		activityTypeDTO.setId(0);
		activityTypeDTO.setName("");
	}

	public ActivityTypeDTOBuilder withId(Integer param) {
		activityTypeDTO.setId(param);
		return this;
	}

	public ActivityTypeDTOBuilder withName(String param) {
		activityTypeDTO.setName(param);
		return this;
	}

	public ActivityTypeDTO now() {
		return activityTypeDTO;
	}
}