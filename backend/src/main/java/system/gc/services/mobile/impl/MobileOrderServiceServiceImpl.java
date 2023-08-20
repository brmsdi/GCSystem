package system.gc.services.mobile.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.ItemDTO;
import system.gc.dtos.OrderServiceDTO;
import system.gc.entities.Employee;
import system.gc.entities.OrderService;
import system.gc.entities.RepairRequest;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderService;
import system.gc.repositories.OrderServiceRepository;
import system.gc.services.mobile.MobileItemService;
import system.gc.services.mobile.MobileOrderServiceService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
@Log4j2
public class MobileOrderServiceServiceImpl implements MobileOrderServiceService {

    @Autowired
    private OrderServiceRepository orderServiceRepository;

    @Autowired
    private MobileItemService mobileItemService;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public Page<OrderServiceDTO> employeeOrders(Pageable pageable, Integer id) {
        Page<OrderService> orders = orderServiceRepository.findAllByEmployee(pageable, id);
        if (orders.isEmpty()) {
            return Page.empty();
        }
        orderServiceRepository.loadLazyWithStatus(orders.toList());
        return orders.map(OrderServiceDTO::forViewListMobile);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderServiceDTO detailsOrderService(Integer idOrderService, Integer idEmployee) throws AccessDeniedOrderService {
        log.info("Buscando detalhes");
        Optional<OrderService> optionalOrderService = orderServiceRepository.details(idOrderService);
        OrderService orderService = optionalOrderService.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_ORDER_SERVICE_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        boolean isResponsible = isResponsible(idEmployee, orderService.getEmployees());
        if (!isResponsible) {
            throw new AccessDeniedOrderService(messageSource.getMessage("ACCESS_DENIED", null, LocaleContextHolder.getLocale()));
        }
        return new OrderServiceDTO(orderService);
    }

    @Override
    public boolean isResponsible(Integer idEmployee, Set<Employee> employees) {
        return employees
                .stream()
                .map(Employee::getId)
                .collect(Collectors.toSet())
                .contains(idEmployee);
    }

    @Override
    @Transactional
    public ItemDTO addItem(Integer idEmployee, Integer idOrderService, Integer idRepairRequest, ItemDTO itemDTO) throws AccessDeniedOrderService {
        log.info("Adicionando item a ordem de serviço");
        Optional<OrderService> optionalOrderService = orderServiceRepository.findByRepairRequest(idOrderService, idRepairRequest);
        OrderService orderService = optionalOrderService.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_ORDER_SERVICE_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        boolean isResponsible = isResponsible(idEmployee, orderService.getEmployees());
        if (!isResponsible) {
            throw new AccessDeniedOrderService(messageSource.getMessage("ACCESS_DENIED", null, LocaleContextHolder.getLocale()));
        }
        RepairRequest repairRequest = orderService.getRepairRequests()
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REPAIR_REQUEST_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        return new ItemDTO(mobileItemService.save(ItemDTO.toEntityWithRepairRequest(itemDTO, repairRequest)));
    }
}