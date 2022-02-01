package system.gc.dtos;

import system.gc.entities.Localization;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

        if(localizationDTO.getId() != null) {
            localization.setId(localizationDTO.getId());
        }
        return localization;
    }
}
