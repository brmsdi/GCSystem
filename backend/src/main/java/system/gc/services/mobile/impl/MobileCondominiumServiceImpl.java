package system.gc.services.mobile.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.CondominiumDTO;
import system.gc.entities.Condominium;
import system.gc.repositories.CondominiumRepository;
import system.gc.services.mobile.MobileCondominiumService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
public class MobileCondominiumServiceImpl implements MobileCondominiumService {

    private final CondominiumRepository condominiumRepository;

    public MobileCondominiumServiceImpl(CondominiumRepository condominiumRepository) {
        this.condominiumRepository = condominiumRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<CondominiumDTO> findAllToScreen(Integer idLessee) {
        List<Condominium> condominiumList = condominiumRepository.findAllForLessee(idLessee);
        return condominiumList.stream().map(CondominiumDTO::forRepairRequestViewListMobile).collect(Collectors.toSet());
    }

    @Override
    public List<Condominium> findAllToScreenEntity(Integer idLessee) {
        return condominiumRepository.findAllForLessee(idLessee);
    }

    @Override
    public boolean exists(Integer id) {
        return condominiumRepository.findById(id).isPresent();
    }
}