package system.gc.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MobileRepairRequestToSaveDTO {
    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String problemDescription;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String apartmentNumber;

    private Integer condominiumID;

    private Integer typeProblemID;

    public MobileRepairRequestToSaveDTO() {}
}