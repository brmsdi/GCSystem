package system.gc.services.mobile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import system.gc.dtos.ContractDTO;
import system.gc.dtos.ContractDocumentDTO;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileContractService {
    Page<ContractDTO> lesseeContracts(Pageable pageable, Integer idLessee);

    ContractDocumentDTO findByIdForLessee(Integer idLessee, Integer idContract);
}
