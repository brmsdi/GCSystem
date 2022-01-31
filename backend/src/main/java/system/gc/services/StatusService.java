package system.gc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.dtos.StatusDTO;
import system.gc.repositories.StatusRepository;

import java.util.List;

@Service
public class StatusService{

    @Autowired
    private StatusRepository statusRepository;

    public List<StatusDTO> findAll() {
        return statusRepository.findAll().stream().map(StatusDTO::new).toList();
    }
}
