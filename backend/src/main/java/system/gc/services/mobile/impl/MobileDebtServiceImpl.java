package system.gc.services.mobile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.DebtDTO;
import system.gc.entities.Debt;
import system.gc.repositories.DebtRepository;
import system.gc.services.mobile.MobileDebtService;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
public class MobileDebtServiceImpl implements MobileDebtService {
    private final DebtRepository debtRepository;

    @Autowired
    public MobileDebtServiceImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DebtDTO> lesseeDebts(Pageable pageable, Integer idLessee) {
        Page<Debt> debtPage = debtRepository.findDebtsForLessee(pageable, idLessee);
        if (debtPage.isEmpty()) {
            return Page.empty();
        }
        debtRepository.loadLazyDebts(debtPage.toList());
        return debtPage.map(DebtDTO::forListViewMobile);
    }
}
