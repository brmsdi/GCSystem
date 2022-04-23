package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.LocalizationDTO;
import system.gc.entities.Localization;
import system.gc.repositories.LocalizationRepository;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Localization save(Localization newLocalization) {
        log.info("Inserindo registro de localização no banco de dados: " + newLocalization.getName());
        Localization localization = localizationRepository.save(newLocalization);;
        log.info("Salvo com sucesso. ID: " + localization.getZipCode());
        return localization;
    }

    public List<LocalizationDTO> findAll() {
        return localizationRepository.findAll().stream().map(LocalizationDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Localization findByZipCode(int ID) {
        Optional<Localization> localizationOptional = localizationRepository.findById(ID);
        return localizationOptional.orElse(null);
    }
}
