package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.controllers.ControllerPermission;
import system.gc.dtos.OrderServiceDTO;
import system.gc.services.web.impl.WebOrderServiceService;
import javax.validation.Valid;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@RestController
@RequestMapping(value = API_V1_WEB + "/order-services")
@Slf4j
public class WebOrderServiceController implements ControllerPermission {

    @Autowired
    private WebOrderServiceService webOrderServiceService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<Page<OrderServiceDTO>> listPaginationOrderServices (
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        return ResponseEntity.ok(webOrderServiceService.listPaginationOrderServices(PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody OrderServiceDTO orderServiceDTO) {
        if (webOrderServiceService.save(orderServiceDTO) == null) {
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
        webOrderServiceService.update(orderServiceDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<OrderServiceDTO>> search(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                    @RequestParam(name = "id") Integer ID) {
        log.info("Localizando ordem de serviço");
        return ResponseEntity.ok(webOrderServiceService.searchOrderService(ID));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        webOrderServiceService.delete(ID);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PostMapping(value = "order-service/close")
    public ResponseEntity<String> closeOrderService(@RequestBody OrderServiceDTO orderServiceDTO)
    {
        webOrderServiceService.closeOrderService(orderServiceDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_CLOSE_ORDER_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }
}
