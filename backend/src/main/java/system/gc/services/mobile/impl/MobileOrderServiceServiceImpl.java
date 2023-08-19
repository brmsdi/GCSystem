package system.gc.services.mobile.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.dtos.OrderServiceDTO;
import system.gc.entities.Employee;
import system.gc.entities.OrderService;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderService;
import system.gc.repositories.OrderServiceRepository;
import system.gc.services.mobile.MobileOrderServiceService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
@Log4j2
public class MobileOrderServiceServiceImpl implements MobileOrderServiceService {

    private final OrderServiceRepository orderServiceRepository;

    public MobileOrderServiceServiceImpl(OrderServiceRepository orderServiceRepository) {
        this.orderServiceRepository = orderServiceRepository;
    }
    @Override
    public Page<OrderServiceDTO> employeeOrders(Pageable pageable, Integer id) {
        Page<OrderService> orders = orderServiceRepository.findAllByEmployee(pageable, id);
        if (orders.isEmpty()) {
            return Page.empty();
        }
        orderServiceRepository.loadLazyWithStatus(orders.toList());
        return orders.map(OrderServiceDTO::forViewListMobile);
    }

    @Override
    public OrderServiceDTO detailsOrderService(Integer idOrderService, Integer idEmployee) throws AccessDeniedOrderService {
        log.info("Buscando detalhes");
        Optional<OrderService> optionalOrderService = orderServiceRepository.details(idOrderService);
        OrderService orderService = optionalOrderService.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado."));
        boolean isResponsible = orderService.getEmployees()
                .stream()
                .map(Employee::getId)
                .collect(Collectors.toSet())
                .contains(idEmployee);
        if (!isResponsible) {
            throw new AccessDeniedOrderService("Acesso negado!");
        }
        return new OrderServiceDTO(orderService);
    }
}