package system.gc.services.mobile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.ContractDTO;
import system.gc.entities.Contract;
import system.gc.repositories.ContractRepository;
import system.gc.services.mobile.MobileContractService;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
public class MobileContractServiceImpl implements MobileContractService {
    private final ContractRepository contractRepository;

    @Autowired
    public MobileContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractDTO> lesseeContracts(Pageable pageable, Integer idLessee) {
        Page<Contract> contractPage = contractRepository.findContractsForLessee(pageable, idLessee);
        if (contractPage.isEmpty()) {
            return Page.empty();
        }
        contractRepository.loadLazyContracts(contractPage.toList());
        return contractPage.map(ContractDTO::forListViewMobile);
    }
}
