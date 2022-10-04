package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Map;
import java.util.Arrays;
import system.gc.dtos.OpenAndProgressAndLateRepairRequest;

public class OpenAndProgressAndLateRepairRequestBuilder {
	private OpenAndProgressAndLateRepairRequest openAndProgressAndLateRepairRequest;
	private OpenAndProgressAndLateRepairRequestBuilder(){}

	public static OpenAndProgressAndLateRepairRequestBuilder newInstance() {
		OpenAndProgressAndLateRepairRequestBuilder builder = new OpenAndProgressAndLateRepairRequestBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(OpenAndProgressAndLateRepairRequestBuilder builder) {
		builder.openAndProgressAndLateRepairRequest = new OpenAndProgressAndLateRepairRequest();
		OpenAndProgressAndLateRepairRequest openAndProgressAndLateRepairRequest = builder.openAndProgressAndLateRepairRequest;
		
		openAndProgressAndLateRepairRequest.setOpenRepairRequest(0);
		openAndProgressAndLateRepairRequest.setProgressRepairRequest(0);
		openAndProgressAndLateRepairRequest.setLateRepairRequest(0);
		openAndProgressAndLateRepairRequest.setValues(null);
	}

	public OpenAndProgressAndLateRepairRequestBuilder withOpenRepairRequest(Integer param) {
		openAndProgressAndLateRepairRequest.setOpenRepairRequest(param);
		return this;
	}

	public OpenAndProgressAndLateRepairRequestBuilder withProgressRepairRequest(Integer param) {
		openAndProgressAndLateRepairRequest.setProgressRepairRequest(param);
		return this;
	}

	public OpenAndProgressAndLateRepairRequestBuilder withLateRepairRequest(Integer param) {
		openAndProgressAndLateRepairRequest.setLateRepairRequest(param);
		return this;
	}

	public OpenAndProgressAndLateRepairRequestBuilder withValues(Map param) {
		openAndProgressAndLateRepairRequest.setValues(param);
		return this;
	}

	public OpenAndProgressAndLateRepairRequest now() {
		return openAndProgressAndLateRepairRequest;
	}
}