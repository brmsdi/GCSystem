package system.gc.services.web.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.TypeProblemDTO;
import system.gc.entities.TypeProblem;
import system.gc.repositories.TypeProblemRepository;

import java.util.List;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
@Slf4j
public class TypeProblemService {

    @Autowired
    private TypeProblemRepository typeProblemRepository;

    public void save(TypeProblemDTO typeProblemDTO) {
        typeProblemRepository.save(new TypeProblemDTO().toEntity(typeProblemDTO));
    }

    @Transactional
    public void save(List<TypeProblem> typeProblemList)
    {
        typeProblemRepository.saveAll(typeProblemList);
    }


    @Transactional(readOnly = true)
    public List<TypeProblemDTO> findAll(Sort sort) {
        return typeProblemRepository.findAll(sort).stream().map(TypeProblemDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public TypeProblem findByName(String name) {
        return typeProblemRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        typeProblemRepository.deleteAll();
    }
}
