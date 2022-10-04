package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import java.lang.String;
import system.gc.dtos.TypeProblemDTO;

public class TypeProblemDTOBuilder {
	private TypeProblemDTO typeProblemDTO;
	private TypeProblemDTOBuilder(){}

	public static TypeProblemDTOBuilder newInstance() {
		TypeProblemDTOBuilder builder = new TypeProblemDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(TypeProblemDTOBuilder builder) {
		builder.typeProblemDTO = new TypeProblemDTO();
		TypeProblemDTO typeProblemDTO = builder.typeProblemDTO;
		
		typeProblemDTO.setId(0);
		typeProblemDTO.setName("");
	}

	public TypeProblemDTOBuilder withId(Integer param) {
		typeProblemDTO.setId(param);
		return this;
	}

	public TypeProblemDTOBuilder withName(String param) {
		typeProblemDTO.setName(param);
		return this;
	}

	public TypeProblemDTO now() {
		return typeProblemDTO;
	}
}