package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.configuration.exceptions.IllegalSelectedRepairRequestsException;
import system.gc.dtos.*;
import system.gc.entities.Employee;
import system.gc.entities.OrderService;
import system.gc.entities.RepairRequest;
import system.gc.entities.Status;
import system.gc.repositories.OrderServiceRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class OrderServiceService {

    @Autowired
    private OrderServiceRepository orderServiceRepository;

    @Autowired
    private RepairRequestService repairRequestService;

    @Autowired
    private StatusService statusService;

    @Transactional
    public OrderServiceDTO save(OrderServiceDTO orderServiceDTO) {
        log.info("Gerando ordem de serviço");
        if (!repairRequestService.checkIfTheRequestIsOpen(orderServiceDTO.getRepairRequests())) {
            throw new IllegalSelectedRepairRequestsException("Solicitações de reparo com o status invalido. Selecione as solicitações com o status 'Aberto'");
        }
        OrderService orderServiceRegistered = orderServiceRepository.save(new OrderServiceDTO().toEntity(orderServiceDTO));
        log.info("Ordem de serviço gerada com o id: " + orderServiceRegistered.getId());
        Status statusProgress = statusService.findByName("Em andamento");
        orderServiceRegistered.getRepairRequests().forEach(repairRequest -> {
            repairRequest.setStatus(statusProgress);
            repairRequest.setOrderService(orderServiceRegistered);
        });
        log.info("Atualizando o status das solicitações de reparo");
        repairRequestService.update(orderServiceRegistered.getRepairRequests());
        return new OrderServiceDTO(orderServiceRegistered);
    }

    @Transactional
    public void update(OrderServiceDTO orderServiceDTO) {
        log.info("Atualizando registro da ordem de serviço");
        Optional<OrderService> optionalOrderService = orderServiceRepository.findById(orderServiceDTO.getId());
        optionalOrderService.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        OrderService orderService = optionalOrderService.get();
        orderService.setGenerationDate(orderServiceDTO.getGenerationDate());
        orderService.setReservedDate(orderServiceDTO.getReservedDate());
        orderService.setCompletionDate(orderService.getCompletionDate());
        orderServiceRepository.save(orderService);
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
    public void closeOrderService(OrderServiceDTO orderServiceDTO) {
        log.info("Registrando conclusão da ordem de serviço");
        Optional<OrderService> optionalOrderService = orderServiceRepository.findById(orderServiceDTO.getId());
        optionalOrderService.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        OrderService orderService = optionalOrderService.get();
        orderServiceRepository.loadLazyOrders(List.of(orderService));
        Status statusConcluded = statusService.findByName("Concluído");
        orderService.getRepairRequests().forEach(repairRequestDTO -> repairRequestDTO.setStatus(statusConcluded));
        orderService.setStatus(statusConcluded);
        orderService.setCompletionDate(new Date());
        orderServiceRepository.save(orderService);
        log.info("Atualizando o status das solicitações de reparo");
        repairRequestService.update(orderService.getRepairRequests());
    }

    @Transactional
    public void cancelOrderService(OrderServiceDTO orderServiceDTO) {
        log.info("Registrando cancelamento da ordem de serviço");
        Optional<OrderService> optionalOrderService = orderServiceRepository.findById(orderServiceDTO.getId());
        optionalOrderService.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        OrderService orderService = optionalOrderService.get();
        orderServiceRepository.loadLazyOrders(List.of(orderService));
        List<StatusDTO> listStatus = statusService.findAllToView(List.of("Aberto", "Cancelado"));
        StatusDTO openStatus = new StatusDTO();
        StatusDTO canceledStatus = new StatusDTO();
        for (StatusDTO statusDTO : listStatus) {
            if (statusDTO.getName().equalsIgnoreCase("Aberto")) {
                openStatus = statusDTO;
            } else if (statusDTO.getName().equalsIgnoreCase("Cancelado")) {
                canceledStatus = statusDTO;
            }
        } // end for
        Status finalOpenStatus = new StatusDTO().toEntity(openStatus);
        Status finalCanceledStatus = new StatusDTO().toEntity(canceledStatus);
        orderService.setStatus(finalCanceledStatus);
        orderService.getRepairRequests().forEach(repairRequestDTO -> {
            repairRequestDTO.setStatus(finalOpenStatus);
            repairRequestDTO.setOrderService(null);
        });
        log.info("Atualizando o status das solicitações de reparo");
        repairRequestService.update(orderService.getRepairRequests());
        orderService.setRepairRequests(null);
        orderServiceRepository.save(orderService);
    } /// end cancelOrderService

    public void updateRepairRequestsFromOrderService(OrderServiceDTO orderServiceDTO) {
        log.info("Atualizando solicitações de reparo da ordem de serviço");
        Optional<OrderService> optionalOrderService = orderServiceRepository.findById(orderServiceDTO.getId());
        optionalOrderService.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        OrderService orderService = optionalOrderService.get();
        OrderService updateOrderService = new OrderServiceDTO().toEntity(orderServiceDTO);
        orderServiceRepository.loadLazyOrders(List.of(orderService));
        List<StatusDTO> listStatus = statusService.findAllToView(List.of("Aberto", "Em andamento"));
        StatusDTO openStatus = new StatusDTO();
        StatusDTO progressStatus = new StatusDTO();
        for (StatusDTO statusDTO : listStatus)
        {
            if (statusDTO.getName().equalsIgnoreCase("Aberto")) {
                openStatus = statusDTO;
            } else if (statusDTO.getName().equalsIgnoreCase("Em andamento")) {
                progressStatus = statusDTO;
            }
        } // end for
        StatusDTO finalOpenStatus = openStatus;
        StatusDTO finalProgressStatus = progressStatus;
        addOrRemoveRepairRequests(orderService, updateOrderService, finalOpenStatus, finalProgressStatus);
        repairRequestService.update(orderService.getRepairRequests());
        orderService.getRepairRequests().removeIf(repairRequest -> repairRequest.getStatus().getId().equals(finalOpenStatus.getId()));
        orderServiceRepository.save(orderService);
    }

    /**
     * @param orderService Ordem de serviço registrada na banco de dados. A mesma terá as solicitações de reparo modificadas
     * @param updateOrderService Ordem de serviço com a lista de solicitações de reparo modificadas
     * @param finalOpenStatus Solicitações de reparo para o status 'Aberto'
     * @param finalProgressStatus Solicitações de reparo para o status 'Em progresso'
     */
    private void addOrRemoveRepairRequests(OrderService orderService, OrderService updateOrderService, StatusDTO finalOpenStatus, StatusDTO finalProgressStatus)
    {
        log.info("Verificando quais as solcitações de reparo serão adicionadas");
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
                for (RepairRequest itemRepairRequests : orderService.getRepairRequests())
                {
                    if (itemRepairRequests.getId().equals(registeredRepairRequest.getId()))
                    {
                        itemRepairRequests.setStatus(new StatusDTO().toEntity(finalOpenStatus));
                        itemRepairRequests.setOrderService(null);
                        break;
                    }
                }
            }
        } // end for external
    }

    public void updateEmployeesFromOrderService(OrderServiceDTO orderServiceDTO) {
        log.info("Atualizando solicitações de reparo da ordem de serviço");
        Optional<OrderService> optionalOrderService = orderServiceRepository.findById(orderServiceDTO.getId());
        optionalOrderService.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        OrderService orderService = optionalOrderService.get();
        OrderService updateOrderService = new OrderServiceDTO().toEntity(orderServiceDTO);
        orderServiceRepository.loadLazyOrders(List.of(orderService));
        addOrRemoveEmployees(orderService, updateOrderService);
        orderServiceRepository.save(orderService);
    }

    private void addOrRemoveEmployees(OrderService orderService, OrderService updateOrderService)
    {
        log.info("Atualizando os funcionários da OS");
        orderService.getEmployees().clear();
        orderService.getEmployees().addAll(updateOrderService.getEmployees());
    }
}
