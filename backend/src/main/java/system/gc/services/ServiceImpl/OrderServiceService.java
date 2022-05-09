package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.configuration.exceptions.IllegalSelectedRepairRequestsException;
import system.gc.dtos.*;
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
}
