package system.gc.services.mobile.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.TypeProblemDTO;
import system.gc.repositories.TypeProblemRepository;
import system.gc.services.mobile.MobileTypeProblemService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
@Service
public class MobileTypeProblemServiceImpl implements MobileTypeProblemService {

    private final TypeProblemRepository typeProblemRepository;

    public MobileTypeProblemServiceImpl(TypeProblemRepository typeProblemRepository) {
        this.typeProblemRepository = typeProblemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<TypeProblemDTO> findAllToScreen() {
        return typeProblemRepository.findAll().stream().map(TypeProblemDTO::new).collect(Collectors.toSet());
    }
}