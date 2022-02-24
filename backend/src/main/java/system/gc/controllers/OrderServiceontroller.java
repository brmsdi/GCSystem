package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.dtos.ContractDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.dtos.OrderServiceDTO;
import system.gc.services.ServiceImpl.OrderServiceService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/order-services")
@Slf4j
public class OrderServiceontroller {

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

    @GetMapping(value = "search")
    public ResponseEntity<Page<OrderServiceDTO>> search(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                    @RequestParam(name = "id") Integer ID) {
        log.info("Localizando ordem de serviço");
        return ResponseEntity.ok(orderServiceService.searchOrderService(ID));
    }

}