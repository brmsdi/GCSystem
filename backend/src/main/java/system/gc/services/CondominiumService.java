package system.gc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.dtos.CondominiumDTO;
import system.gc.entities.Condominium;
import system.gc.repositories.CondominiumRepository;

import javax.transaction.Transactional;

@Service
@Slf4j
public class CondominiumService {

    @Autowired
    private CondominiumRepository condominiumRepository;

    @Transactional
    public CondominiumDTO save(CondominiumDTO condominiumDTO) {
        log.info("Salvando novo registro de condomínio no banco de dados: " + condominiumDTO.getName());
        CondominiumDTO condominiumDTOService = new CondominiumDTO();
        Condominium registeredCondominium = condominiumRepository.save(condominiumDTOService.toEntity(condominiumDTO));
        if(registeredCondominium.getId() == null) {
            log.warn("Erro ao salvar!");
            return null;
        }
        log.info("Salvo com sucesso. ID: " + registeredCondominium.getId());
        return condominiumDTOService.toDTO(registeredCondominium);
    }

    @Transactional
    public Page<CondominiumDTO> findAllPagination(Pageable pageable) {
        log.info("Bunscando condominios");
        Page<Condominium> pageCondominium = condominiumRepository.findAll(pageable);
        condominiumRepository.findCondominiumPagination(pageCondominium.toList());
        return pageCondominium.map(CondominiumDTO::new);
    }

}
