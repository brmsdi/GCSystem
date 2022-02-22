package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.dtos.OrderServiceDTO;
import system.gc.entities.OrderService;
import system.gc.entities.RepairRequest;
import system.gc.entities.Status;
import system.gc.repositories.OrderServiceRepository;

import javax.transaction.Transactional;

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
            throw new IllegalArgumentException("Solicitações de reparo com o status invalido. Selecione as solicitações com o status 'Aberto'");
        }
        OrderService orderServiceRegistered = orderServiceRepository.save(new OrderServiceDTO().toEntity(orderServiceDTO));
        log.info("Ordem de serviço gerada com o id: " + orderServiceRegistered.getId());
        Status statusProgress = statusService.findByName("Em andamento");
        for (RepairRequest repairRequest : orderServiceRegistered.getRepairRequests()) {
            repairRequest.setStatus(statusProgress);
            repairRequest.setOrderService(orderServiceRegistered);
        }
        log.info("Atualizando o status das solicitações de reparo");
        repairRequestService.update(orderServiceRegistered.getRepairRequests());
        return new OrderServiceDTO(orderServiceRegistered);
    }

}
