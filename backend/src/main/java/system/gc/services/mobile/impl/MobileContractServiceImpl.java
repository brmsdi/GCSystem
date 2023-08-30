package system.gc.services.mobile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.ContractDTO;
import system.gc.entities.Contract;
import system.gc.repositories.ContractRepository;
import system.gc.services.mobile.MobileContractService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
public class MobileContractServiceImpl implements MobileContractService {
    private final ContractRepository contractRepository;
    private final MessageSource messageSource;

    @Autowired
    public MobileContractServiceImpl(ContractRepository contractRepository, MessageSource messageSource) {
        this.contractRepository = contractRepository;
        this.messageSource = messageSource;
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

    @Override
    public ContractDTO findByIdForLessee(Integer idLessee, Integer idContract) {
        Optional<Contract> contractOptional = contractRepository.findByIdForLessee(idLessee, idContract);
        Contract contract = contractOptional.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        contractRepository.loadLazyContracts(List.of(contract));
        return ContractDTO.forContractDocument(contract);
    }
}
