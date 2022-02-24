package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.dtos.LesseeDTO;
import system.gc.dtos.RepairRequestDTO;
import system.gc.entities.RepairRequest;
import system.gc.entities.Status;
import system.gc.repositories.RepairRequestRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class RepairRequestService {
    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @Autowired
    private StatusService statusService;

    @Transactional
    public RepairRequestDTO save(RepairRequestDTO repairRequestDTO) {
        log.info("Salvando nova solicitação de reparo");
        RepairRequestDTO repairRequestDTOService = new RepairRequestDTO();
        repairRequestDTO.setDate(new Date());
        RepairRequest registeredRepairRequest = repairRequestRepository.save(repairRequestDTOService.toEntity(repairRequestDTO));
        if (registeredRepairRequest.getId() == null) {
            log.warn("Erro ao salvar!");
            return null;
        }
        log.info("Salvo com sucesso. ID: " + registeredRepairRequest.getId());
        return repairRequestDTOService.toDTO(registeredRepairRequest);
    }

    @Transactional
    public Page<RepairRequestDTO> listPaginationRepairRequest(Pageable pageable) {
        log.info("Buscando solicitações de reparo");
        Page<RepairRequest> repairRequestPage = repairRequestRepository.findAll(pageable);
        repairRequestRepository.loadLazyRepairRequests(repairRequestPage.toList());
        return repairRequestPage.map(RepairRequestDTO::new);
    }

    @Transactional
    public void update(RepairRequestDTO repairRequestDTO) throws EntityNotFoundException {
        Optional<RepairRequest> repairRequest = repairRequestRepository.findById(repairRequestDTO.getId());
        repairRequest.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        repairRequestRepository.save(new RepairRequestDTO().toEntity(repairRequestDTO));
    }

    @Transactional
    public void update(Set<RepairRequest> repairRequests) throws EntityNotFoundException {
        repairRequestRepository.saveAll(repairRequests);
    }

    @Transactional
    public Page<RepairRequestDTO> searchRepairRequest(Pageable pageable, LesseeDTO lesseeDTO) {
        log.info("Buscando registro de solicitações");
        Page<RepairRequest> repairRequestPage = repairRequestRepository.findRepairRequestForLessee(pageable, lesseeDTO.getCpf());
        repairRequestRepository.loadLazyRepairRequests(repairRequestPage.toList());
        return repairRequestPage.map(RepairRequestDTO::new);
    }

    @Transactional
    public void delete(Integer ID) throws EntityNotFoundException {
        log.info("Deletando registro com o ID: " + ID);
        Optional<RepairRequest> repairRequestOptional = repairRequestRepository.findById(ID);
        repairRequestOptional.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        repairRequestRepository.delete(repairRequestOptional.get());
        log.info("Registro deletado com sucesso");
    }

    @Transactional
    public boolean checkIfTheRequestIsOpen(Set<RepairRequestDTO> repairRequestDTOSet) {
        Status statusOpen = statusService.findByName("Aberto");
        log.info("Verificando as solicitações em aberto");
        List<RepairRequest> repairRequestList = new ArrayList<>();
        for (RepairRequestDTO repairRequestDTO : repairRequestDTOSet) {
            repairRequestList.add(new RepairRequestDTO().toEntity(repairRequestDTO));
        }
        List<RepairRequest> repairRequestListResult = repairRequestRepository.checkIfTheRequestIsOpen(repairRequestList, statusOpen.getId());
        return repairRequestList.size() == repairRequestListResult.size();
    }
}