package system.gc.services.web.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.exceptionsAdvice.exceptions.IllegalSelectedRepairRequestsException;
import system.gc.dtos.*;
import system.gc.entities.OrderService;
import system.gc.entities.RepairRequest;
import system.gc.entities.Status;
import system.gc.repositories.OrderServiceRepository;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static system.gc.utils.TextUtils.*;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
@Slf4j
public class OrderServiceService {

    @Autowired
    private OrderServiceRepository orderServiceRepository;

    @Autowired
    private RepairRequestService repairRequestService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private MessageSource messageSource;

    private AtomicReference<Status> openStatus = null;

    private AtomicReference<Status> canceledStatus = null;

    private AtomicReference<Status> concludedStatus = null;

    private AtomicReference<Status> progressStatus = null;

    @Transactional
    public OrderServiceDTO save(OrderServiceDTO orderServiceDTO) {
        log.info("Gerando ordem de serviço");
        if (!repairRequestService.checkIfTheRequestIsOpen(orderServiceDTO.getRepairRequests())) {
            throw new IllegalSelectedRepairRequestsException(messageSource.getMessage("TEXT_ERROR_REPAIR_REQUEST_STATUS_INVALID", null, LocaleContextHolder.getLocale()));
        }
        OrderService orderServiceRegistered = orderServiceRepository.save(new OrderServiceDTO().toEntity(orderServiceDTO));
        log.info("Ordem de serviço gerada com o id: " + orderServiceRegistered.getId());
        Status statusProgress = statusService.findByName(STATUS_IN_PROGRESS);
        orderServiceRegistered
                .getRepairRequests()
                .parallelStream()
                .forEach(repairRequest -> {
                    repairRequest.setStatus(statusProgress);
                    repairRequest.setOrderService(orderServiceRegistered);
                });
        log.info("Atualizando o status das solicitações de reparo");
        repairRequestService.update(orderServiceRegistered.getRepairRequests());
        return new OrderServiceDTO(orderServiceRegistered);
    }

    @Transactional
    public OrderServiceDTO update(OrderServiceDTO updateOrderServiceDTO) {
        log.info("Atualizando registro da ordem de serviço");
        Optional<OrderService> optionalOrderService = orderServiceRepository.findById(updateOrderServiceDTO.getId());
        optionalOrderService.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        OrderService orderService = optionalOrderService.get();
        orderServiceRepository.loadLazyOrders(List.of(orderService));
        if (updateOrderServiceDTO.getStatus().getName().equalsIgnoreCase(STATUS_CONCLUDED))
        {
            initializeStatus(List.of(STATUS_CONCLUDED));
            closeOrderService(orderService);
        } else if(updateOrderServiceDTO.getStatus().getName().equalsIgnoreCase(STATUS_CANCELED))
        {
            initializeStatus(List.of(STATUS_OPEN, STATUS_CANCELED));
            cancelOrderService(orderService);
        } else {
            initializeStatus(List.of(STATUS_OPEN, STATUS_IN_PROGRESS));
            updateRepairRequestsFromOrderService(orderService, updateOrderServiceDTO);
            updateEmployeesFromOrderService(orderService, updateOrderServiceDTO);
        }

        orderService.setGenerationDate(updateOrderServiceDTO.getGenerationDate());
        orderService.setReservedDate(updateOrderServiceDTO.getReservedDate());
        orderService.setCompletionDate(orderService.getCompletionDate());
        return new OrderServiceDTO(orderServiceRepository.save(orderService));
    }

    @Transactional
    public Page<OrderServiceDTO> listPaginationOrderServices(Pageable pageable) {
        log.info("Listando ordens de serviço");
        Page<OrderService> orderServicePage = orderServiceRepository.findAll(pageable);
        orderServiceRepository.loadLazyOrders(orderServicePage.toList());
        return orderServicePage.map(OrderServiceDTO::forViewOrders);
    }

    @Transactional
    public Page<OrderServiceDTO> searchOrderService(Integer ID) {
        log.info("Buscando registro de ordem de serviços");
        Optional<OrderService> orderServiceOptional = orderServiceRepository.findById(ID);
        if (orderServiceOptional.isPresent()) {
            orderServiceRepository.loadLazyOrders(List.of(orderServiceOptional.get()));
            return new PageImpl<>(List.of(OrderServiceDTO.forViewOrders(orderServiceOptional.get())));
        }
        return Page.empty();
    }

    @Transactional
    public void closeOrderService(OrderService orderService) {
        log.info("Registrando conclusão da ordem de serviço");
        orderService.getRepairRequests().parallelStream().forEach(repairRequestDTO -> repairRequestDTO.setStatus(concludedStatus.get()));
        orderService.setStatus(concludedStatus.get());
        orderService.setCompletionDate(new Date());
        log.info("Atualizando o status das solicitações de reparo");
        repairRequestService.update(orderService.getRepairRequests());
    }

    @Transactional
    public void closeOrderService(OrderServiceDTO orderServiceDTO)
    {
        Optional<OrderService> optionalOrderService = orderServiceRepository.findById(orderServiceDTO.getId());
        optionalOrderService.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        OrderService orderService = optionalOrderService.get();
        initializeStatus(List.of(STATUS_CONCLUDED));
        closeOrderService(orderService);
        orderServiceRepository.save(orderService);
    }

    @Transactional
    public void cancelOrderService(OrderService orderService) {
        log.info("Registrando cancelamento da ordem de serviço");
        orderService.setStatus(canceledStatus.get());
        orderService.getRepairRequests().forEach(repairRequestDTO -> {
            repairRequestDTO.setStatus(openStatus.get());
            repairRequestDTO.setOrderService(null);
        });
        log.info("Atualizando o status das solicitações de reparo");
        repairRequestService.update(orderService.getRepairRequests());
        orderService.setRepairRequests(null);
    } /// end cancelOrderService

    @Transactional
    public void delete(Integer ID)
    {
        log.info("Deletando registro da ordem de serviço");
        Optional<OrderService> optionalOrderService = orderServiceRepository.findById(ID);
        optionalOrderService.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        OrderService orderService = optionalOrderService.get();
        orderServiceRepository.loadLazyOrders(List.of(orderService));
        List<StatusDTO> listStatus = statusService.findAllToViewDTO(List.of(STATUS_OPEN, STATUS_IN_PROGRESS));
        StatusDTO openStatus = new StatusDTO();
        StatusDTO progressStatus = new StatusDTO();
        for (StatusDTO statusDTO : listStatus)
        {
            if (statusDTO.getName().equalsIgnoreCase(STATUS_OPEN)) {
                openStatus = statusDTO;
            } else if (statusDTO.getName().equalsIgnoreCase(STATUS_IN_PROGRESS)) {
                progressStatus = statusDTO;
            }
        } // end for
        StatusDTO finalOpenStatus = openStatus;
        StatusDTO finalProgressStatus = progressStatus;
        for (RepairRequest repairRequest : orderService.getRepairRequests())
        {
            if (repairRequest.getStatus().getId().equals(finalProgressStatus.getId()))
            {
                repairRequest.setStatus(new StatusDTO().toEntity(finalOpenStatus));
                repairRequest.setOrderService(null);
            }
        }
        repairRequestService.update(orderService.getRepairRequests());
        orderService.getRepairRequests().removeIf(repairRequest -> repairRequest.getStatus().getId().equals(finalOpenStatus.getId()));
        if (!orderService.getRepairRequests().isEmpty())
        {
            repairRequestService.delete(orderService.getRepairRequests().stream().map(RepairRequest::getId).toList());
        }
        orderService.getRepairRequests().clear();
        orderServiceRepository.delete(orderService);
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        orderServiceRepository.deleteAll();
    }

    public void updateRepairRequestsFromOrderService(OrderService orderService, OrderServiceDTO updateOrderServiceDTO) {
        log.info("Atualizando solicitações de reparo da ordem de serviço");
        OrderService updateOrderService = new OrderServiceDTO().toEntity(updateOrderServiceDTO);
        StatusDTO finalOpenStatus = new StatusDTO(openStatus.get());
        StatusDTO finalProgressStatus = new StatusDTO(progressStatus.get());
        addOrRemoveRepairRequests(orderService, updateOrderService, finalOpenStatus, finalProgressStatus);
        repairRequestService.update(orderService.getRepairRequests());
        orderService.getRepairRequests().removeIf(repairRequest -> repairRequest.getStatus().getId().equals(finalOpenStatus.getId()));
    }

    /**
     * @param orderService Ordem de serviço registrada na banco de dados. A mesma terá as solicitações de reparo modificadas
     * @param updateOrderService Ordem de serviço com a lista de solicitações de reparo modificadas
     * @param finalOpenStatus Solicitações de reparo para o status 'Aberto'
     * @param finalProgressStatus Solicitações de reparo para o status 'Em andamento'
     */
    private void addOrRemoveRepairRequests(OrderService orderService, OrderService updateOrderService, StatusDTO finalOpenStatus, StatusDTO finalProgressStatus)
    {
        log.info("Verificando quais as solicitações de reparo serão adicionadas");
        for (RepairRequest newRepairRequest: updateOrderService.getRepairRequests())
        {
            boolean add = true;
            for (RepairRequest registeredRepairRequest : orderService.getRepairRequests())
            {
                if (newRepairRequest.getId().equals(registeredRepairRequest.getId()))
                {
                    add = false;
                    break;
                }
            } // end for internal
            if (add)
            {
                newRepairRequest.setStatus(new StatusDTO().toEntity(finalProgressStatus));
                newRepairRequest.setOrderService(orderService);
                orderService.getRepairRequests().add(newRepairRequest);
            }
        } // end for external
        log.info("Verificando quais as solicitações de reparo serão removidas");
        for (RepairRequest registeredRepairRequest: orderService.getRepairRequests())
        {
            boolean remove = true;
            for (RepairRequest newRepairRequest : updateOrderService.getRepairRequests())
            {
                if (newRepairRequest.getId().equals(registeredRepairRequest.getId()))
                {
                    remove = false;
                    break;
                }
            } // end for internal
            if (remove)
            {
                registeredRepairRequest.setStatus(new StatusDTO().toEntity(finalOpenStatus));
                registeredRepairRequest.setOrderService(null);
            }
        } // end for external
    }

    public void updateEmployeesFromOrderService(OrderService orderService, OrderServiceDTO updateOrderServiceDTO) {
        log.info("Atualizando solicitações de reparo da ordem de serviço");
        OrderService updateOrderService = new OrderServiceDTO().toEntity(updateOrderServiceDTO);
        addOrRemoveEmployees(orderService, updateOrderService);
    }

    private void addOrRemoveEmployees(OrderService orderService, OrderService updateOrderService)
    {
        log.info("Atualizando os funcionários da OS");
        orderService.getEmployees().clear();
        orderService.getEmployees().addAll(updateOrderService.getEmployees());
    }

    private void initializeStatus(List<String> statusNameList)
    {
        List<Status> statusList = statusService.findAllToView(statusNameList);
        statusList.parallelStream().forEach(status -> {
            if (status.getName().equalsIgnoreCase(STATUS_OPEN)) {
                openStatus = new AtomicReference<>(new Status());
                openStatus.set(status);
            } else if (status.getName().equalsIgnoreCase(STATUS_CANCELED)) {
                canceledStatus = new AtomicReference<>(new Status());
                canceledStatus.set(status);
            } else if (status.getName().equalsIgnoreCase(STATUS_CONCLUDED))
            {
                concludedStatus = new AtomicReference<>(new Status());
                concludedStatus.set(status);
            } else if (status.getName().equalsIgnoreCase(STATUS_IN_PROGRESS)) {
                progressStatus = new AtomicReference<>(new Status());
                progressStatus.set(status);
            }
        });
    }
}
