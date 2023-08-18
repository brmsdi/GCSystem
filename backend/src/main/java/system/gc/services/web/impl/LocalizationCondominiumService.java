package system.gc.services.web.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import system.gc.repositories.LocalizationCondominiumRepository;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
@Slf4j
public class LocalizationCondominiumService {
    
    @Autowired
    private LocalizationCondominiumRepository localizationCondominiumRepository;

    @Transactional
    public void daleteAll()
    {
        log.info("Deletando todos");
        localizationCondominiumRepository.deleteAll();
    }
}
