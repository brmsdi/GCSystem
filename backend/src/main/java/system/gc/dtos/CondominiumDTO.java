package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Condominium;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
public class CondominiumDTO implements ConvertEntityAndDTO<CondominiumDTO, Condominium> {
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
    private StatusDTO status;

    @NotNull(message = "{required.validation}")
    private LocalizationCondominiumDTO localization;

    public CondominiumDTO() {}

    public CondominiumDTO(String name) {
        setName(name);
    }

    public CondominiumDTO(String name, String description, int numberApartments, StatusDTO status, LocalizationCondominiumDTO localization) {
        setName(name);
        setDescription(description);
        setNumberApartments(numberApartments);
        setStatus(status);
        setLocalization(localization);
    }

    public CondominiumDTO(Condominium condominium) {
        setId(condominium.getId());
        setName(condominium.getName());
        setDescription(condominium.getDescription());
        setNumberApartments(condominium.getNumberApartments());
        setStatus(new StatusDTO(condominium.getStatus()));
        setLocalization(new LocalizationCondominiumDTO(condominium.getLocalizationCondominium()));
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
                new StatusDTO().toEntity(condominiumDTO.getStatus()),
                new LocalizationCondominiumDTO().toEntity(condominiumDTO.getLocalization()),
                null);
        if (condominiumDTO.getId() != null) {
            condominium.setId(condominiumDTO.getId());
        }
        return condominium;
    }

    public static CondominiumDTO forRepairRequestViewListMobile(Condominium condominium) {
        CondominiumDTO condominiumDTO = new CondominiumDTO();
        condominiumDTO.setId(condominium.getId());
        condominiumDTO.setName(condominium.getName());
        return condominiumDTO;
    }

}
