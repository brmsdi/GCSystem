package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderServiceDTO implements ConvertEntityAndDTO<OrderServiceDTO, OrderService> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    private Date generationDate;

    private Date reservedDate;

    private Date completionDate;

    private Set<RepairRequestDTO> repairRequests;

    private Set<EmployeeDTO> employees;

    private StatusDTO status;

    public OrderServiceDTO() {}

    public OrderServiceDTO(Date generationDate, Date reservedDate, Set<RepairRequestDTO> repairRequests, Set<EmployeeDTO> employees, StatusDTO statusDTO) {
        setGenerationDate(generationDate);
        setReservedDate(reservedDate);
        setRepairRequests(repairRequests);
        setEmployees(employees);
        setStatus(statusDTO);
    }

    public OrderServiceDTO(OrderService orderService) {
        setId(orderService.getId());
        setGenerationDate(orderService.getGenerationDate());
        setReservedDate(orderService.getReservedDate());
        setCompletionDate(orderService.getCompletionDate());
        setRepairRequests(new HashSet<>());
        for (RepairRequest repairRequest : orderService.getRepairRequests()) {
            getRepairRequests().add(new RepairRequestDTO(repairRequest));
        }
        setEmployees(new HashSet<>());
        EmployeeDTO employeeDTO;
        for (Employee employee : orderService.getEmployees()) {
            employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setName(employee.getName());
            getEmployees().add(employeeDTO);
        }
        setStatus(new StatusDTO().toDTO(orderService.getStatus()));
    }

    public static OrderServiceDTO forViewOrders(OrderService orderService) {
        OrderServiceDTO orderServiceDTO = new OrderServiceDTO();
        orderServiceDTO.setId(orderService.getId());
        orderServiceDTO.setGenerationDate(orderService.getGenerationDate());
        orderServiceDTO.setReservedDate(orderService.getReservedDate());
        orderServiceDTO.setCompletionDate(orderService.getCompletionDate());
        Set<EmployeeDTO> employeeDTOSet = new HashSet<>();
        EmployeeDTO employeeDTO;
        for (Employee employee : orderService.getEmployees()) {
            employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setName(employee.getName());
            employeeDTO.setRole(new RoleDTO(employee.getRole()));
            employeeDTOSet.add(employeeDTO);

        }
        orderServiceDTO.setEmployees(employeeDTOSet);
        Set<RepairRequestDTO> repairRequestDTOSet = new HashSet<>();
        RepairRequestDTO repairRequestDTO;
        for (RepairRequest repairRequest : orderService.getRepairRequests()) {
            repairRequestDTO = new RepairRequestDTO();
            repairRequestDTO.setId(repairRequest.getId());
            repairRequestDTO.setProblemDescription(repairRequest.getProblemDescription());
            repairRequestDTO.setTypeProblem(new TypeProblemDTO().toDTO(repairRequest.getTypeProblem()));
            repairRequestDTO.setDate(repairRequest.getDate());
            repairRequestDTO.setApartmentNumber(repairRequest.getApartmentNumber());
            repairRequestDTO.setStatus(new StatusDTO().toDTO(repairRequest.getStatus()));
            if (repairRequest.getItems() != null || !repairRequest.getItems().isEmpty()) {
                repairRequestDTO.setItems(new ItemDTO().convertSetEntityToSetEntityDTO(repairRequest.getItems()));
            }
            LesseeDTO lesseeDTO = new LesseeDTO();
            lesseeDTO.setId(repairRequest.getLessee().getId());
            lesseeDTO.setName(repairRequest.getLessee().getName());
            lesseeDTO.setContactNumber(repairRequest.getLessee().getContactNumber());
            repairRequestDTO.setLessee(lesseeDTO);
            CondominiumDTO condominiumDTO = new CondominiumDTO();
            condominiumDTO.setId(repairRequest.getCondominium().getId());
            condominiumDTO.setName(repairRequest.getCondominium().getName());
            repairRequestDTO.setCondominium(condominiumDTO);
            repairRequestDTOSet.add(repairRequestDTO);
        }
        orderServiceDTO.setRepairRequests(repairRequestDTOSet);
        orderServiceDTO.setStatus(new StatusDTO().toDTO(orderService.getStatus()));
        return orderServiceDTO;
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
        orderService.setCompletionDate(orderServiceDTO.getCompletionDate());
        orderService.setRepairRequests(new HashSet<>());
        for (RepairRequestDTO repairRequestDTO : orderServiceDTO.getRepairRequests()) {
            orderService.getRepairRequests().add(new RepairRequestDTO().toEntity(repairRequestDTO));
        }
        orderService.setEmployees(new HashSet<>());
        Employee employee;
        for (EmployeeDTO employeeDTO : orderServiceDTO.getEmployees()) {
            employee = new Employee();
            employee.setId(employeeDTO.getId());
            employee.setName(employeeDTO.getName());
            orderService.getEmployees().add(employee);
        }
        orderService.setStatus(new StatusDTO().toEntity(orderServiceDTO.getStatus()));
        if (orderServiceDTO.getId() != null) {
            orderService.setId(orderServiceDTO.getId());
        }
        return orderService;
    }
}
