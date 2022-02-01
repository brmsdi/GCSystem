package system.gc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.dtos.CondominiumDTO;
import system.gc.entities.Condominium;
import system.gc.repositories.CondominiumRepository;

@Service
@Slf4j
public class CondominiumService {

    @Autowired
    private CondominiumRepository condominiumRepository;

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
}
