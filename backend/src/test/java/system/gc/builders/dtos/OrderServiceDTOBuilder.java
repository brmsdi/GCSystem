package system.gc.builders.dtos;

import java.lang.Integer;
import java.util.Arrays;
import java.util.Set;
import java.util.Date;
import system.gc.dtos.StatusDTO;
import system.gc.dtos.OrderServiceDTO;

public class OrderServiceDTOBuilder {
	private OrderServiceDTO orderServiceDTO;
	private OrderServiceDTOBuilder(){}

	public static OrderServiceDTOBuilder newInstance() {
		OrderServiceDTOBuilder builder = new OrderServiceDTOBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	public static void initializeDefaultData(OrderServiceDTOBuilder builder) {
		builder.orderServiceDTO = new OrderServiceDTO();
		OrderServiceDTO orderServiceDTO = builder.orderServiceDTO;
		
		orderServiceDTO.setId(0);
		orderServiceDTO.setGenerationDate(null);
		orderServiceDTO.setReservedDate(null);
		orderServiceDTO.setCompletionDate(null);
		orderServiceDTO.setRepairRequests(null);
		orderServiceDTO.setEmployees(null);
		orderServiceDTO.setStatus(null);
	}

	public OrderServiceDTOBuilder withId(Integer param) {
		orderServiceDTO.setId(param);
		return this;
	}

	public OrderServiceDTOBuilder withGenerationDate(Date param) {
		orderServiceDTO.setGenerationDate(param);
		return this;
	}

	public OrderServiceDTOBuilder withReservedDate(Date param) {
		orderServiceDTO.setReservedDate(param);
		return this;
	}

	public OrderServiceDTOBuilder withCompletionDate(Date param) {
		orderServiceDTO.setCompletionDate(param);
		return this;
	}

	public OrderServiceDTOBuilder withRepairRequests(Set param) {
		orderServiceDTO.setRepairRequests(param);
		return this;
	}

	public OrderServiceDTOBuilder withEmployees(Set param) {
		orderServiceDTO.setEmployees(param);
		return this;
	}

	public OrderServiceDTOBuilder withStatus(StatusDTO param) {
		orderServiceDTO.setStatus(param);
		return this;
	}

	public OrderServiceDTO now() {
		return orderServiceDTO;
	}
}