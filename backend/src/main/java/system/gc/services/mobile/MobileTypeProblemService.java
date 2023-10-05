package system.gc.services.mobile;

import system.gc.dtos.TypeProblemDTO;
import system.gc.entities.TypeProblem;

import java.util.List;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileTypeProblemService {

    Set<TypeProblemDTO> findAllToScreen();

    List<TypeProblem> findAllToScreenEntity();

    boolean exists(Integer id);
}
