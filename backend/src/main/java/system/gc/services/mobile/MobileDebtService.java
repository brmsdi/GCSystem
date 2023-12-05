package system.gc.services.mobile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import system.gc.dtos.DebtDTO;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileDebtService {

    Page<DebtDTO> lesseeDebts(Pageable pageable, Integer idLessee);
}
