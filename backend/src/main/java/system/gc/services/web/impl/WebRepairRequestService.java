package system.gc.services.web.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

import static system.gc.utils.TextUtils.*;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
@Slf4j
public class WebRepairRequestService {
    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @Autowired
    private WebStatusService webStatusService;

    @Autowired
    private WebItemService webItemService;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public RepairRequestDTO save(RepairRequestDTO repairRequestDTO) {
        log.info("Salvando nova solicitação de reparo");
        RepairRequestDTO repairRequestDTOService = new RepairRequestDTO();
        RepairRequest registeredRepairRequest = repairRequestRepository.save(repairRequestDTOService.toEntity(repairRequestDTO));
        if (registeredRepairRequest.getId() == null) {
            log.warn("Erro ao salvar!");
            return null;
        }
        registeredRepairRequest.getItems().forEach(item -> item.setRepairRequest(registeredRepairRequest));
        webItemService.saveAll(registeredRepairRequest.getItems());
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
        repairRequest.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        // IMPEDE QUE O STATUS DE SOLICITAÇÕES EM ANDAMENTO SEJAM ATUALIZADAS.
        // SOLICITAÇÕES VÍNCULADAS À UMA ORDEM DE SERVIÇO NÃO PODERÁ TER O STATUS MODIFICADO.
        if (repairRequest.get().getStatus().getName().equalsIgnoreCase(STATUS_IN_PROGRESS))
        {
            repairRequestDTO.setStatus(new StatusDTO().toDTO(repairRequest.get().getStatus()));
        }
        RepairRequest repairRequestUpdate = new RepairRequestDTO().toEntity(repairRequestDTO);
        repairRequestUpdate.getItems().forEach(item -> {
            if (item.getRepairRequest() == null) {
                item.setRepairRequest(repairRequestUpdate);
            }
        });
        repairRequestRepository.save(repairRequestUpdate);
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
        repairRequestOptional.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
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
        Status statusOpen = webStatusService.findByName(STATUS_OPEN);
        log.info("Verificando as solicitações em aberto");
        List<RepairRequest> repairRequestList = new ArrayList<>();
        repairRequestDTOSet
                .parallelStream()
                .forEach(repairRequestDTO -> repairRequestList.add(new RepairRequestDTO().toEntity(repairRequestDTO)));
        Long size = repairRequestRepository.checkIfTheRequestIsOpen(repairRequestList, statusOpen.getId());
        return repairRequestList.size() == Integer.parseInt("" + size);
    }

    @Transactional(readOnly = true)
    public OpenAndProgressAndLateRepairRequest openAndProgressAndLateRepairRequest(List<String> params) {
        log.info("Buscando lista de status");
        List<StatusDTO> statusDTOList = webStatusService.findAllToViewDTO(params);
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
        openAndProgressAndLateRepairRequest.generate(STATUS_OPEN, STATUS_IN_PROGRESS, STATUS_LATE);
        return openAndProgressAndLateRepairRequest;
    }

    public List<RepairRequestDTO> findAllToModalOrderService(List<String> statusName) {
        List<StatusDTO> listStatus = webStatusService.findAllToViewDTO(statusName);
        List<RepairRequest> repairRequestList = repairRequestRepository.perStatusRepairRequest(listStatus.stream().map(StatusDTO::getId).toList());
        repairRequestRepository.loadLazyRepairRequests(repairRequestList);
        return repairRequestList.stream().map(RepairRequestDTO::new).toList();
    }

    public List<RepairRequestDTO> findAllPerOrderServiceAndStatus(Integer ID, List<String> statusName) {
        List<StatusDTO> listStatus = webStatusService.findAllToViewDTO(statusName);
        List<RepairRequest> repairRequestList = repairRequestRepository.findAllPerOrderServiceAndStatus(ID, listStatus.stream().map(StatusDTO::getId).toList());
        repairRequestRepository.loadLazyRepairRequests(repairRequestList);
        return repairRequestList.stream().map(RepairRequestDTO::new).toList();
    }
}