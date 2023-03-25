package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.LocalizationCondominium;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
public class LocalizationCondominiumDTO implements ConvertEntityAndDTO<LocalizationCondominiumDTO, LocalizationCondominium> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String number;

    @NotNull(message = "{required.validation}")
    private LocalizationDTO localization;

    public LocalizationCondominiumDTO() {
    }

    public LocalizationCondominiumDTO(String number, LocalizationDTO localization) {
        setNumber(number);
        setLocalization(localization);
    }

    public LocalizationCondominiumDTO(LocalizationCondominium localizationCondominium) {
        setNumber(localizationCondominium.getNumber());
        setLocalization(new LocalizationDTO(localizationCondominium.getLocalization()));
    }

    @Override
    public LocalizationCondominiumDTO toDTO(LocalizationCondominium localizationCondominium) {
        return new LocalizationCondominiumDTO(localizationCondominium);
    }

    @Override
    public LocalizationCondominium toEntity(LocalizationCondominiumDTO localizationCondominiumDTO) {
        LocalizationCondominium localizationCondominium = new LocalizationCondominium(
                localizationCondominiumDTO.getNumber(),
                new LocalizationDTO().toEntity(localizationCondominiumDTO.getLocalization()));
        if (localizationCondominiumDTO.getId() != null) {
            localizationCondominium.setId(localizationCondominiumDTO.getId());
        }
        return localizationCondominium;
    }
}
