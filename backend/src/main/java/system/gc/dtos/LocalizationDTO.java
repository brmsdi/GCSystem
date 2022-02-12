package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Localization;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LocalizationDTO implements ConvertEntityAndDTO<LocalizationDTO, Localization> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String road;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String zipCode;

    public LocalizationDTO() {
    }

    public LocalizationDTO(String name, String road, String zipCode) {
        setName(name);
        setRoad(road);
        setZipCode(zipCode);
    }

    public LocalizationDTO(Localization localization) {
        setId(localization.getId());
        setName(localization.getName());
        setRoad(localization.getRoad());
        setZipCode(localization.getZipCode());
    }

    @Override
    public LocalizationDTO toDTO(Localization localization) {
        return new LocalizationDTO(localization);
    }

    @Override
    public Localization toEntity(LocalizationDTO localizationDTO) {
        Localization localization = new Localization(localizationDTO.getName(),
                localizationDTO.getRoad(),
                localizationDTO.getZipCode());

        if (localizationDTO.getId() != null) {
            localization.setId(localizationDTO.getId());
        }
        return localization;
    }
}
