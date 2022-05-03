package system.gc.services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.StatusDTO;
import system.gc.entities.Status;
import system.gc.repositories.StatusRepository;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Transactional(readOnly = true)
    public List<StatusDTO> findAll(Sort sort) {
        return statusRepository.findAll(sort).stream().map(StatusDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Status findByName(String name) {
        return statusRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<StatusDTO> findAllFromView(List<String> params) {
        List<Status> statusList = statusRepository.findAllFromViewCondominium(params);
        return new StatusDTO().convertListEntityFromListDTO(statusList);
    }
}
