package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Localization;
import javax.validation.constraints.*;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
public class LocalizationDTO implements ConvertEntityAndDTO<LocalizationDTO, Localization> {

    @NotNull(message = "{required.validation}")
    private int zipCode;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String road;

    public LocalizationDTO() {
    }

    public LocalizationDTO(String name, String road, int zipCode) {
        setName(name);
        setRoad(road);
        setZipCode(zipCode);
    }

    public LocalizationDTO(Localization localization) {
        setZipCode(localization.getZipCode());
        setName(localization.getName());
        setRoad(localization.getRoad());
    }

    @Override
    public LocalizationDTO toDTO(Localization localization) {
        return new LocalizationDTO(localization);
    }

    @Override
    public Localization toEntity(LocalizationDTO localizationDTO) {
        return new Localization(localizationDTO.getName(),
                localizationDTO.getRoad(),
                localizationDTO.getZipCode());
    }
}
