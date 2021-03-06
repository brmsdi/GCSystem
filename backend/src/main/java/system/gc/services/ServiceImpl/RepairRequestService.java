package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.*;
import system.gc.entities.RepairRequest;
import system.gc.entities.Status;
import system.gc.repositories.RepairRequestRepository;

import javax.persistence.EntityNotFoundException;
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
        // IMPEDE QUE O STATUS DE SOLICITAÇÕES EM ANDAMENTO SEJAM ATUALIZADAS.
        // SOLICITAÇÕES VÍNCULADAS À UMA ORDEM DE SERVIÇO NÃO PODERÁ TER O STATUS MODIFICADO.
        if (repairRequest.get().getStatus().getName().equalsIgnoreCase("Em andamento"))
        {
            repairRequestDTO.setStatus(new StatusDTO().toDTO(repairRequest.get().getStatus()));
        }
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
    public void delete(List<Integer> IDS) throws EntityNotFoundException {
        log.info("Deletando solicitações de reparo");
        repairRequestRepository.deleteAllById(IDS);
        log.info("Registros deletados com sucesso");
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        repairRequestRepository.deleteAll();
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

    @Transactional(readOnly = true )
    public OpenAndProgressAndLateRepairRequest openAndProgressAndLateRepairRequest(List<String> params) {
        log.info("Buscando lista de status");
        List<StatusDTO> statusDTOList = statusService.findAllToView(params);
        log.info("Buscando reparos por status");
        List<RepairRequest> repairRequestList = repairRequestRepository.perStatusRepairRequest(statusDTOList.stream().map(StatusDTO::getId).toList());
        OpenAndProgressAndLateRepairRequest openAndProgressAndLateRepairRequest =
                new OpenAndProgressAndLateRepairRequest(0, 0, 0, new HashMap<>());
        statusDTOList.forEach(statusDTO ->  openAndProgressAndLateRepairRequest.getValues().put(statusDTO.getName(), 0));
        for (RepairRequest repairRequest : repairRequestList) {
            String key = repairRequest.getStatus().getName();
            Integer newValue = openAndProgressAndLateRepairRequest.getValues().get(key) + 1;
            openAndProgressAndLateRepairRequest.getValues().replace(key, newValue);
        }
        log.info("Gerando valores");
        openAndProgressAndLateRepairRequest.generate("Aberto", "Em andamento", "Atrasado");
        return openAndProgressAndLateRepairRequest;
    }

    public List<RepairRequestDTO> findAllToModalOrderService(List<String> statusName) {
        List<StatusDTO> listStatus = statusService.findAllToView(statusName);
        List<RepairRequest> repairRequestList = repairRequestRepository.perStatusRepairRequest(listStatus.stream().map(StatusDTO::getId).toList());
        repairRequestRepository.loadLazyRepairRequests(repairRequestList);
        return repairRequestList.stream().map(RepairRequestDTO::new).toList();
    }

    public List<RepairRequestDTO> findAllPerOrderServiceAndStatus(Integer ID, List<String> statusName) {
        List<StatusDTO> listStatus = statusService.findAllToView(statusName);
        List<RepairRequest> repairRequestList = repairRequestRepository.findAllPerOrderServiceAndStatus(ID, listStatus.stream().map(StatusDTO::getId).toList());
        repairRequestRepository.loadLazyRepairRequests(repairRequestList);
        return repairRequestList.stream().map(RepairRequestDTO::new).toList();
    }
}