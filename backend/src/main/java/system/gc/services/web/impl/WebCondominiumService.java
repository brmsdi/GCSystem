package system.gc.services.web.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import system.gc.dtos.CondominiumDTO;
import system.gc.dtos.LocalizationDTO;
import system.gc.entities.Condominium;
import system.gc.entities.Localization;
import system.gc.repositories.CondominiumRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
@Slf4j
public class WebCondominiumService {

    @Autowired
    private CondominiumRepository condominiumRepository;

    @Autowired
    private WebLocalizationService webLocalizationService;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public CondominiumDTO save(CondominiumDTO condominiumDTO) {
        log.info("Salvando novo registro de condomínio no banco de dados: " + condominiumDTO.getName());
        verifyLocalization(condominiumDTO);
        CondominiumDTO condominiumDTOService = new CondominiumDTO();
        Condominium registeredCondominium = condominiumRepository.save(condominiumDTOService.toEntity(condominiumDTO));
        log.info("Salvo com sucesso. ID: " + registeredCondominium.getId());
        return condominiumDTOService.toDTO(registeredCondominium);
    }

    @Transactional
    public Page<CondominiumDTO> listPaginationCondominium(Pageable pageable) {
        log.info("Buscando condominios");
        Page<Condominium> pageCondominium = condominiumRepository.findAll(pageable);
        condominiumRepository.loadLazyCondominiums(pageCondominium.toList());
        return pageCondominium.map(CondominiumDTO::new);
    }

    public List<CondominiumDTO> findAll(Sort sort) {
            return condominiumRepository.findAll(sort).stream().map(CondominiumDTO::new).toList();
    }

    @Transactional
    public void update(CondominiumDTO condominiumDTO) throws EntityNotFoundException {
        Optional<Condominium> condominium = condominiumRepository.findById(condominiumDTO.getId());
        condominium.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        verifyLocalization(condominiumDTO);
        condominiumRepository.save(new CondominiumDTO().toEntity(condominiumDTO));
    }

    @Transactional
    public Page<CondominiumDTO> searchCondominium(Pageable pageable, CondominiumDTO condominiumDTO) {
        log.info("Buscando registro de condimínios com o nome: " + condominiumDTO.getName());
        Page<Condominium> condominiums = condominiumRepository.findAllByName(pageable, condominiumDTO.getName());
        if (condominiums.isEmpty()) {
            log.warn("Registro não encontrado");
            return Page.empty();
        }
        condominiumRepository.loadLazyCondominiums(condominiums.toList());
        return condominiums.map(CondominiumDTO::new);
    }

    @Transactional
    public void delete(Integer ID) throws EntityNotFoundException {
        log.info("Deletando registro com o ID: " + ID);
        Optional<Condominium> condominium = condominiumRepository.findById(ID);
        condominium.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        condominiumRepository.delete(condominium.get());
        log.info("Registro deletado com sucesso");
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        condominiumRepository.deleteAll();
    }

    public void verifyLocalization(CondominiumDTO condominiumDTO)
    {
        Localization localization = webLocalizationService.findByZipCode(condominiumDTO.getLocalization().getLocalization().getZipCode());
        if (localization == null) {
            localization = webLocalizationService.save(new LocalizationDTO().toEntity(condominiumDTO.getLocalization().getLocalization()));
            condominiumDTO.getLocalization().setLocalization(new LocalizationDTO(localization));
        }
    }

}
