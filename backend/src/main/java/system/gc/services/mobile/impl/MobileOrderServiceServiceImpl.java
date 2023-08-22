package system.gc.services.mobile.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.OrderServiceDTO;
import system.gc.entities.OrderService;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.repositories.OrderServiceRepository;
import system.gc.services.mobile.MobileItemService;
import system.gc.services.mobile.MobileOrderServiceService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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
    public OrderServiceDTO detailsOrderService(Integer idOrderService, Integer idEmployee) throws AccessDeniedOrderServiceException {
        log.info("Buscando detalhes");
        Optional<OrderService> optionalOrderService = orderServiceRepository.details(idOrderService);
        OrderService orderService = optionalOrderService.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_ORDER_SERVICE_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        boolean isResponsible = isResponsible(idEmployee, orderService.getEmployees());
        if (!isResponsible) {
            throw new AccessDeniedOrderServiceException(messageSource.getMessage("ACCESS_DENIED", null, LocaleContextHolder.getLocale()));
        }
        return new OrderServiceDTO(orderService);
    }

    @Override
    public Page<OrderServiceDTO> findByIdFromEmployee(Pageable pageable, Integer idEmployee, Integer idOrderService) {
        Page<OrderService> orderServicePage = orderServiceRepository.findByIdFromEmployee(pageable, idEmployee, idOrderService);
        if (orderServicePage.isEmpty()) {
            return Page.empty();
        }
        orderServiceRepository.loadLazyWithStatus(orderServicePage.toList());
        return orderServicePage.map(OrderServiceDTO::forViewListMobile);
    }
}