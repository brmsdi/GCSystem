package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Condominium;
import system.gc.entities.Item;
import system.gc.entities.Lessee;
import system.gc.entities.RepairRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

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

    public RepairRequestDTO() {}

    public RepairRequestDTO(String problemDescription, Date date, TypeProblemDTO typeProblem, LesseeDTO lessee, CondominiumDTO condominium, String apartmentNumber,  StatusDTO status) {
        setProblemDescription(problemDescription);
        setDate(date);
        setTypeProblem(typeProblem);
        setLessee(lessee);
        setCondominium(condominium);
        setStatus(status);
        setApartmentNumber(apartmentNumber);
    }

    public RepairRequestDTO(RepairRequest repairRequest) {
        setId(repairRequest.getId());
        setProblemDescription(repairRequest.getProblemDescription());
        setDate(repairRequest.getDate());
        setTypeProblem(new TypeProblemDTO().toDTO(repairRequest.getTypeProblem()));
        setStatus(new StatusDTO().toDTO(repairRequest.getStatus()));
        LesseeDTO lesseeDTO = new LesseeDTO();
        lesseeDTO.setId(repairRequest.getLessee().getId());
        lesseeDTO.setName(repairRequest.getLessee().getName());
        lesseeDTO.setContactNumber(repairRequest.getLessee().getContactNumber());
        setLessee(lesseeDTO);
        CondominiumDTO condominiumDTO = new CondominiumDTO();
        condominiumDTO.setId(repairRequest.getCondominium().getId());
        condominiumDTO.setName(repairRequest.getCondominium().getName());
        setCondominium(condominiumDTO);
        setApartmentNumber(repairRequest.getApartmentNumber());
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
        lessee.setContactNumber(repairRequestDTO.getLessee().getContactNumber());
        repairRequest.setLessee(lessee);
        Condominium condominium = new Condominium();
        condominium.setId(repairRequestDTO.getCondominium().getId());
        condominium.setName(repairRequestDTO.getCondominium().getName());
        repairRequest.setCondominium(condominium);
        repairRequest.setApartmentNumber(repairRequestDTO.getApartmentNumber());
        repairRequest.setStatus(new StatusDTO().toEntity(repairRequestDTO.getStatus()));
        repairRequest.setItems(new ItemDTO().convertSetEntityDTOFromSetEntity(repairRequestDTO.getItems()));
        if(repairRequestDTO.getId() != null ) {
            repairRequest.setId(repairRequestDTO.getId());
        }
        return repairRequest;
    }
}
