package system.gc.services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.ActivityTypeDTO;
import system.gc.entities.ActivityType;
import system.gc.entities.Status;
import system.gc.repositories.ActivityTypeRepository;
import java.util.List;

@Service
public class ActivityTypeService {

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Transactional(readOnly = true)
    public List<ActivityTypeDTO> findAll() {
        return activityTypeRepository.findAll().stream().map(ActivityTypeDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ActivityType findByName(String name) {
        return activityTypeRepository.getByName(name).orElse(null) ;
    }
}
