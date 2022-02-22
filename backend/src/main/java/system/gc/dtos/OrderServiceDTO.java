package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.OrderService;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class OrderServiceDTO implements ConvertEntityAndDTO<OrderServiceDTO, OrderService> {
    private Integer id;

    private Date generationDate;

    @NotNull(message = "{required.validation}")
    private Date reservedDate;

    private Set<RepairRequestDTO> repairRequests;

    private Set<EmployeeDTO> employees;

    public OrderServiceDTO() {}

    public OrderServiceDTO(Date generationDate, Date reservedDate, Set<RepairRequestDTO> repairRequests, Set<EmployeeDTO> employees) {
        setGenerationDate(generationDate);
        setReservedDate(reservedDate);
        setRepairRequests(repairRequests);
        setEmployees(employees);
    }

    public OrderServiceDTO(OrderService orderService) {
        setId(orderService.getId());
        setGenerationDate(orderService.getGenerationDate());
        setReservedDate(orderService.getReservedDate());
        setRepairRequests(new RepairRequestDTO().convertSetEntityToSetEntityDTO(orderService.getRepairRequests()));
        setEmployees(new EmployeeDTO().convertSetEntityToSetEntityDTO(orderService.getEmployees()));
    }

    @Override
    public OrderServiceDTO toDTO(OrderService orderService) {
        return new OrderServiceDTO(orderService);
    }

    @Override
    public OrderService toEntity(OrderServiceDTO orderServiceDTO) {
        OrderService orderService = new OrderService();
        orderService.setGenerationDate(orderServiceDTO.getGenerationDate());
        orderService.setReservedDate(orderServiceDTO.getReservedDate());
        orderService.setRepairRequests(new RepairRequestDTO().convertSetEntityDTOFromSetEntity(orderServiceDTO.getRepairRequests()));
        orderService.setEmployees(new EmployeeDTO().convertSetEntityDTOFromSetEntity(orderServiceDTO.getEmployees()));
        if (orderServiceDTO.getId() != null) {
            orderService.setId(orderServiceDTO.getId());
        }
        return orderService;
    }
}
