package system.gc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Data
@AllArgsConstructor
public class ScreenNewRepairRequestMobileDataDTO {
    private Set<CondominiumDTO> condominiums;
    private Set<TypeProblemDTO> typeProblems;
}
