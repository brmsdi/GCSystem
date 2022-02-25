package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.configuration.exceptions.IllegalSelectedRepairRequestsException;
import system.gc.dtos.*;
import system.gc.entities.Contract;
import system.gc.entities.OrderService;
import system.gc.entities.RepairRequest;
import system.gc.entities.Status;
import system.gc.repositories.OrderServiceRepository;

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
        log.info("Verificou");
        OrderService orderServiceRegistered = orderServiceRepository.save(new OrderServiceDTO().toEntity(orderServiceDTO));
        log.info("Ordem de serviço gerada com o id: " + orderServiceRegistered.getId());
        Status statusProgress = statusService.findByName("Em andamento");
        Set<RepairRequest> toUpdateRepairRequests = new HashSet<>();
        for (RepairRequest repairRequest : orderServiceRegistered.getRepairRequests()) {
            repairRequest.setStatus(statusProgress);
            repairRequest.setOrderService(orderServiceRegistered);
            toUpdateRepairRequests.add(repairRequest);
        }
        log.info("Atualizando o status das solicitações de reparo");
        repairRequestService.update(toUpdateRepairRequests);
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
        Status statusConcluded = statusService.findByName("Concluído");
        OrderService orderService = new OrderServiceDTO().toEntity(orderServiceDTO);
        orderService.getRepairRequests().forEach(repairRequestDTO -> repairRequestDTO.setStatus(statusConcluded));
        orderService.setStatus(statusConcluded);
        orderService.setCompletationDate(new Date());
        orderServiceRepository.save(orderService);
    }

}
