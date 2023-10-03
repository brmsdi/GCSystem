package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Getter
@Setter
public class RepairRequestDTO implements ConvertEntityAndDTO<RepairRequestDTO, RepairRequest> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String problemDescription;

    private Date date;

    private TypeProblemDTO typeProblem;

    private LesseeDTO lessee;

    private CondominiumDTO condominium;

    private StatusDTO status;

    private Set<ItemDTO> items;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String apartmentNumber;

    private OrderServiceDTO orderService;

    public RepairRequestDTO() {
    }

    public RepairRequestDTO(String problemDescription, Date date, TypeProblemDTO typeProblem, LesseeDTO lessee, CondominiumDTO condominium, String apartmentNumber, StatusDTO status) {
        setProblemDescription(problemDescription);
        setDate(date);
        setTypeProblem(typeProblem);
        setLessee(lessee);
        setCondominium(condominium);
        setApartmentNumber(apartmentNumber);
        setStatus(status);
    }

    public RepairRequestDTO(RepairRequest repairRequest) {
        setId(repairRequest.getId());
        setProblemDescription(repairRequest.getProblemDescription());
        setDate(repairRequest.getDate());
        setApartmentNumber(repairRequest.getApartmentNumber());
        setTypeProblem(new TypeProblemDTO().toDTO(repairRequest.getTypeProblem()));
        setStatus(new StatusDTO().toDTO(repairRequest.getStatus()));
        LesseeDTO lesseeDTO = new LesseeDTO();
        lesseeDTO.setId(repairRequest.getLessee().getId());
        lesseeDTO.setCpf(repairRequest.getLessee().getCpf());
        lesseeDTO.setRg(repairRequest.getLessee().getRg());
        lesseeDTO.setName(repairRequest.getLessee().getName());
        lesseeDTO.setContactNumber(repairRequest.getLessee().getContactNumber());
        setLessee(lesseeDTO);
        CondominiumDTO condominiumDTO = new CondominiumDTO();
        condominiumDTO.setId(repairRequest.getCondominium().getId());
        condominiumDTO.setName(repairRequest.getCondominium().getName());
        condominiumDTO.setNumberApartments(repairRequest.getCondominium().getNumberApartments());
        setCondominium(condominiumDTO);
        setItems(new ItemDTO().convertSetEntityToSetEntityDTO(repairRequest.getItems()));
    }

    @Override
    public RepairRequestDTO toDTO(RepairRequest repairRequest) {
        return new RepairRequestDTO(repairRequest);
    }

    @Override
    public RepairRequest toEntity(RepairRequestDTO repairRequestDTO) {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setProblemDescription(repairRequestDTO.getProblemDescription());
        repairRequest.setDate(repairRequestDTO.getDate());
        repairRequest.setTypeProblem(new TypeProblemDTO().toEntity(repairRequestDTO.getTypeProblem()));
        Lessee lessee = new Lessee();
        lessee.setId(repairRequestDTO.getLessee().getId());
        lessee.setName(repairRequestDTO.getLessee().getName());
        lessee.setRg(repairRequestDTO.getLessee().getRg());
        lessee.setContactNumber(repairRequestDTO.getLessee().getContactNumber());
        repairRequest.setLessee(lessee);
        Condominium condominium = new Condominium();
        condominium.setId(repairRequestDTO.getCondominium().getId());
        condominium.setName(repairRequestDTO.getCondominium().getName());
        repairRequest.setCondominium(condominium);
        repairRequest.setApartmentNumber(repairRequestDTO.getApartmentNumber());
        repairRequest.setStatus(new StatusDTO().toEntity(repairRequestDTO.getStatus()));
        repairRequest.setItems(new ItemDTO().convertSetEntityDTOToSetEntity(repairRequestDTO.getItems()));
        if (repairRequestDTO.getId() != null) {
            repairRequest.setId(repairRequestDTO.getId());
        }
        return repairRequest;
    }

    @Deprecated
    public static RepairRequestDTO toModalOrderService(RepairRequest repairRequest) {
        RepairRequestDTO repairRequestDTO = new RepairRequestDTO();
        repairRequestDTO.setId(repairRequest.getId());
        repairRequestDTO.setProblemDescription(repairRequest.getProblemDescription());
        repairRequestDTO.setDate(repairRequest.getDate());
        repairRequestDTO.setTypeProblem(new TypeProblemDTO(repairRequest.getTypeProblem()));
        return repairRequestDTO;
    }

    public static RepairRequestDTO forViewListMobile(RepairRequest repairRequest) {
        RepairRequestDTO repairRequestDTO = new RepairRequestDTO();
        repairRequestDTO.setId(repairRequest.getId());
        repairRequestDTO.setCondominium(CondominiumDTO.forRepairRequestViewListMobile(repairRequest.getCondominium()));
        repairRequestDTO.setTypeProblem(new TypeProblemDTO(repairRequest.getTypeProblem()));
        repairRequestDTO.setStatus(new StatusDTO(repairRequest.getStatus()));
        return repairRequestDTO;
    }

    public static RepairRequestDTO toDetailsMobile(RepairRequest repairRequest) {
        RepairRequestDTO repairRequestDTO = new RepairRequestDTO();
        repairRequestDTO.setId(repairRequest.getId());
        repairRequestDTO.setCondominium(new CondominiumDTO());
        repairRequestDTO.getCondominium().setId(repairRequest.getCondominium().getId());
        repairRequestDTO.getCondominium().setName(repairRequest.getCondominium().getName());
        repairRequestDTO.setApartmentNumber(repairRequest.getApartmentNumber());
        repairRequestDTO.setProblemDescription(repairRequest.getProblemDescription());
        repairRequestDTO.setDate(repairRequest.getDate());
        repairRequestDTO.setTypeProblem(new TypeProblemDTO().toDTO(repairRequest.getTypeProblem()));
        if (repairRequest.getOrderService() != null && repairRequest.getOrderService().getId() != null) {
            repairRequestDTO.setOrderService(new OrderServiceDTO());
            repairRequestDTO.getOrderService().setId(repairRequest.getOrderService().getId());
            if (!repairRequest.getOrderService().getEmployees().isEmpty()) {
                Set<EmployeeDTO> employeeDTOSet = new HashSet<>();
                repairRequest.getOrderService().getEmployees().forEach(employee -> {
                    EmployeeDTO employeeDTO = new EmployeeDTO();
                    employeeDTO.setId(employee.getId());
                    employeeDTO.setName(employee.getName());
                    employeeDTOSet.add(employeeDTO);
                });
                repairRequestDTO.getOrderService().setEmployees(employeeDTOSet);
            }
        }
        return repairRequestDTO;
    }
}
