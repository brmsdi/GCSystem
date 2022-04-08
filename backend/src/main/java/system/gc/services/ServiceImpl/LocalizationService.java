package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.LocalizationDTO;
import system.gc.entities.Localization;
import system.gc.repositories.LocalizationRepository;

import java.util.List;

@Service
@Slf4j
public class LocalizationService {

    @Autowired
    private LocalizationRepository localizationRepository;

    @Transactional
    public LocalizationDTO save(LocalizationDTO localizationDTO) {
        log.info("Inserindo registro de localização no banco de dados: " + localizationDTO.getName());
        LocalizationDTO localizationDTOService = new LocalizationDTO();
        Localization localization = localizationRepository.save(localizationDTOService.toEntity(localizationDTO));
        //
        log.info("Salvo com sucesso. ID: " + localization.getZipCode());
        return localizationDTOService.toDTO(localization);
    }

    public List<LocalizationDTO> findAll() {
        return localizationRepository.findAll().stream().map(LocalizationDTO::new).toList();
    }
}
