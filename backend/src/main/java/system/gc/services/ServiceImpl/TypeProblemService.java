package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.TypeProblemDTO;
import system.gc.entities.TypeProblem;
import system.gc.repositories.TypeProblemRepository;

import java.util.List;

@Service
@Slf4j
public class TypeProblemService {
    @Autowired
    private TypeProblemRepository typeProblemRepository;

    public void save(TypeProblemDTO typeProblemDTO) {
        typeProblemRepository.save(new TypeProblemDTO().toEntity(typeProblemDTO));
    }

    @Transactional(readOnly = true)
    public List<TypeProblemDTO> findAll() {
        return typeProblemRepository.findAll().stream().map(TypeProblemDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public TypeProblem findByName(String name) {
        return typeProblemRepository.findByName(name).orElse(null);
    }

}
