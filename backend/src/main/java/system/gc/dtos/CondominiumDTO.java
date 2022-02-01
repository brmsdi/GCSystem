package system.gc.dtos;

import system.gc.entities.Condominium;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CondominiumDTO implements ConvertEntityAndDTO<CondominiumDTO, Condominium>{
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String description;

    @NotNull(message = "{required.validation}")
    private int numberApartments;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private StatusDTO statusDTO;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private LocalizationCondominiumDTO localizationCondominiumDTO;

    /*
    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private Set<Contract> contract; */

    public CondominiumDTO() {
    }

    public CondominiumDTO(String name, String description, int numberApartments, StatusDTO statusDTO, LocalizationCondominiumDTO localizationCondominiumDTO) {
        setName(name);
        setDescription(description);
        setNumberApartments(numberApartments);
        setStatusDTO(statusDTO);
        setLocalizationCondominiumDTO(localizationCondominiumDTO);
    }

    public CondominiumDTO(Condominium condominium) {
        setId(condominium.getId());
        setName(condominium.getName());
        setDescription(condominium.getDescription());
        setNumberApartments(condominium.getNumberApartments());
        setStatusDTO(new StatusDTO(condominium.getStatus()));
        setLocalizationCondominiumDTO(new LocalizationCondominiumDTO(condominium.getLocalizationCondominium()));
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberApartments() {
        return numberApartments;
    }

    public void setNumberApartments(int numberApartments) {
        this.numberApartments = numberApartments;
    }

    public StatusDTO getStatusDTO() {
        return statusDTO;
    }

    public void setStatusDTO(StatusDTO statusDTO) {
        this.statusDTO = statusDTO;
    }

    public LocalizationCondominiumDTO getLocalizationCondominiumDTO() {
        return localizationCondominiumDTO;
    }

    public void setLocalizationCondominiumDTO(LocalizationCondominiumDTO localizationCondominiumDTO) {
        this.localizationCondominiumDTO = localizationCondominiumDTO;
    }

    @Override
    public CondominiumDTO toDTO(Condominium condominium) {
        return new CondominiumDTO(condominium);
    }

    @Override
    public Condominium toEntity(CondominiumDTO condominiumDTO) {
        Condominium condominium = new Condominium(condominiumDTO.getName(),
                condominiumDTO.getDescription(),
                condominiumDTO.getNumberApartments(),
                new StatusDTO().toEntity(condominiumDTO.getStatusDTO()),
                new LocalizationCondominiumDTO().toEntity(condominiumDTO.getLocalizationCondominiumDTO()),
                null);
        if(condominiumDTO.getId() != null) {
            condominium.setId(condominiumDTO.getId());
        }
        return condominium;
    }
}
