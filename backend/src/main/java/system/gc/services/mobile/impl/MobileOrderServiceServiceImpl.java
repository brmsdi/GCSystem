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
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;
import system.gc.repositories.OrderServiceRepository;
import system.gc.services.mobile.MobileOrderServiceService;
import system.gc.services.web.impl.WebOrderServiceService;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static system.gc.utils.TextUtils.STATUS_CONCLUDED;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
@Log4j2
public class MobileOrderServiceServiceImpl implements MobileOrderServiceService {
    private final OrderServiceRepository orderServiceRepository;
    private final WebOrderServiceService webOrderServiceService;
    private final MessageSource messageSource;

    @Autowired
    public MobileOrderServiceServiceImpl(OrderServiceRepository orderServiceRepository,
                                         WebOrderServiceService webOrderServiceService,
                                         MessageSource messageSource) {
        this.orderServiceRepository = orderServiceRepository;
        this.webOrderServiceService = webOrderServiceService;
        this.messageSource = messageSource;
    }

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
        isResponsible(idEmployee, orderService.getEmployees(), messageSource);
        return new OrderServiceDTO(orderService);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderServiceDTO> findByIdFromEmployee(Pageable pageable, Integer idEmployee, Integer idOrderService) {
        Page<OrderService> orderServicePage = orderServiceRepository.findByIdFromEmployee(pageable, idEmployee, idOrderService);
        if (orderServicePage.isEmpty()) {
            return Page.empty();
        }
        orderServiceRepository.loadLazyWithStatus(orderServicePage.toList());
        return orderServicePage.map(OrderServiceDTO::forViewListMobile);
    }

    @Override
    @Transactional
    public void closeOrderService(Integer idEmployee, Integer idOrderService) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException {
        Optional<OrderService> optionalOrderService = orderServiceRepository.details(idOrderService);
        OrderService orderService = optionalOrderService.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        statusIsEditable(STATUS_CONCLUDED, orderService.getStatus(), messageSource);
        isResponsible(idEmployee, orderService.getEmployees(), messageSource);
        webOrderServiceService.closeOrderService(orderService);
        orderServiceRepository.save(orderService);
    }
}