package system.gc.services.mobile;

import system.gc.dtos.CondominiumDTO;
import system.gc.entities.Condominium;

import java.util.List;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileCondominiumService {

    Set<CondominiumDTO> findAllToScreen(Integer idLessee);

    List<Condominium> findAllToScreenEntity(Integer idLessee);

    boolean exists(Integer id);
}
