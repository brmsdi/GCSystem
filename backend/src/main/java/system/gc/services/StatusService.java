package system.gc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.StatusDTO;
import system.gc.entities.Status;
import system.gc.repositories.StatusRepository;

import java.util.List;

@Service
public class StatusService{

    @Autowired
    private StatusRepository statusRepository;

    @Transactional(readOnly = true)
    public List<StatusDTO> findAll() {
        return statusRepository.findAll().stream().map(StatusDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Status findByName(String name) {
        return statusRepository.getByName(name).orElse(null) ;
    }
}
