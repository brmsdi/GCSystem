package system.gc.services.web.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.StatusDTO;
import system.gc.entities.Status;
import system.gc.repositories.StatusRepository;

import java.util.List;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
@Slf4j
public class WebStatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Transactional
    public void save(List<Status> statusList)
    {
        log.info("Inserindo todos os status no banco de dados");
        statusRepository.saveAll(statusList);
    }

    @Transactional(readOnly = true)
    public List<StatusDTO> findAll(Sort sort) {
        return statusRepository.findAll(sort).stream().map(StatusDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Status findByName(String name) {
        return statusRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<StatusDTO> findAllToViewDTO(List<String> params) {
        List<Status> statusList = statusRepository.findAllToView(params);
        return new StatusDTO().convertListEntityToListDTO(statusList);
    }

    @Transactional(readOnly = true)
    public List<Status> findAllToView(List<String> params) {
        return statusRepository.findAllToView(params).stream().toList();
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        statusRepository.deleteAll();
    }
}
