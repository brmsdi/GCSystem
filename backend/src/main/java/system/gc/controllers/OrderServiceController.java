package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.dtos.OrderServiceDTO;
import system.gc.services.ServiceImpl.OrderServiceService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/order-services")
@Slf4j
public class OrderServiceController implements ControllerPermission {

    @Autowired
    private OrderServiceService orderServiceService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<Page<OrderServiceDTO>> listPaginationOrderServices (
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        return ResponseEntity.ok(orderServiceService.listPaginationOrderServices(PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody OrderServiceDTO orderServiceDTO) {
        if (orderServiceService.save(orderServiceDTO) == null) {
            return ResponseEntity.ok(messageSource.getMessage("TEXT_ERROR_INSERT_ORDER_SERVICE",
                    null,
                    LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_INSERT_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody OrderServiceDTO orderServiceDTO) {
        log.info("Atualizando registro");
        orderServiceService.update(orderServiceDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<OrderServiceDTO>> search(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                    @RequestParam(name = "id") Integer ID) {
        log.info("Localizando ordem de serviço");
        return ResponseEntity.ok(orderServiceService.searchOrderService(ID));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        orderServiceService.delete(ID);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PostMapping(value = "order-service/close")
    public ResponseEntity<String> closeOrderService(@RequestBody OrderServiceDTO orderServiceDTO)
    {
        orderServiceService.closeOrderService(orderServiceDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_CLOSE_ORDER_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }
/*
    @PostMapping(value = "order-service/cancel")
    public ResponseEntity<String> cancelOrderService(@RequestBody  OrderServiceDTO orderServiceDTO)
    {
        orderServiceService.cancelOrderService(orderServiceDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_CANCELED_ORDER_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PostMapping(value = "order-service/repair-requests/change")
    public ResponseEntity<String> updateRepairRequestsFromOrderService(@Valid @RequestBody OrderServiceDTO orderServiceDTO) {
        log.info("Atualizando solicitações de reparo da OS");
        orderServiceService.updateRepairRequestsFromOrderService(orderServiceDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PostMapping(value = "order-service/employees/change")
    public ResponseEntity<String> updateEmployeesFromOrderService(@Valid @RequestBody OrderServiceDTO orderServiceDTO) {
        log.info("Atualizando solicitações de reparo da OS");
        orderServiceService.updateEmployeesFromOrderService(orderServiceDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

 */
}
