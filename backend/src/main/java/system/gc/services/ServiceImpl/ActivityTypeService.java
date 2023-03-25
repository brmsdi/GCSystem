package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.ActivityTypeDTO;
import system.gc.entities.ActivityType;
import system.gc.repositories.ActivityTypeRepository;
import java.util.List;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Service
@Slf4j
public class ActivityTypeService {

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Transactional
    public void save(List<ActivityType> activityTypeList)
    {
        log.info("Inserindo todos tipos de atividades no banco de dados");
        activityTypeRepository.saveAll(activityTypeList);
    }

    @Transactional(readOnly = true)
    public List<ActivityTypeDTO> findAll() {
        return activityTypeRepository.findAll().stream().map(ActivityTypeDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ActivityType findByName(String name) {
        return activityTypeRepository.findByName(name).orElse(null) ;
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        activityTypeRepository.deleteAll();
    }
}
