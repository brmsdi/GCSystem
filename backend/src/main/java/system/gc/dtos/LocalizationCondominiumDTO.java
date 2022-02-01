package system.gc.dtos;

import system.gc.entities.LocalizationCondominium;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LocalizationCondominiumDTO implements ConvertEntityAndDTO<LocalizationCondominiumDTO, LocalizationCondominium> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String number;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private LocalizationDTO localizationDTO;

    public LocalizationCondominiumDTO() {
    }

    public LocalizationCondominiumDTO(String number, LocalizationDTO localizationDTO) {
        setNumber(number);
        setLocalizationDTO(localizationDTO);
    }

    public LocalizationCondominiumDTO(LocalizationCondominium localizationCondominium) {
        setId(localizationCondominium.getId());
        setNumber(localizationCondominium.getNumber());
        setLocalizationDTO(new LocalizationDTO(localizationCondominium.getLocalization()));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalizationDTO getLocalizationDTO() {
        return localizationDTO;
    }

    public void setLocalizationDTO(LocalizationDTO localizationDTO) {
        this.localizationDTO = localizationDTO;
    }

    @Override
    public LocalizationCondominiumDTO toDTO(LocalizationCondominium localizationCondominium) {
        return new LocalizationCondominiumDTO(localizationCondominium);
    }

    @Override
    public LocalizationCondominium toEntity(LocalizationCondominiumDTO localizationCondominiumDTO) {
        LocalizationCondominium localizationCondominium = new LocalizationCondominium(
                localizationCondominiumDTO.getNumber(),
                new LocalizationDTO().toEntity(localizationCondominiumDTO.getLocalizationDTO()));
        if(localizationCondominiumDTO.getId() != null) {
            localizationCondominium.setId(localizationDTO.getId());
        }
        return localizationCondominium;
    }
}
