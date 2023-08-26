package system.gc.services.mobile;

import system.gc.dtos.CondominiumDTO;

import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileCondominiumService {

    Set<CondominiumDTO> findAllToScreen(Integer idLessee);
}
