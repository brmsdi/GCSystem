package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.controllers.ControllerPermission;
import system.gc.dtos.CondominiumDTO;
import system.gc.services.web.impl.WebCondominiumService;
import javax.validation.Valid;
import java.util.List;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@RestController
@RequestMapping(value = API_V1_WEB + "/condominiums")
@Slf4j
public class WebCondominiumController implements ControllerPermission {

    @Autowired
    private WebCondominiumService webCondominiumService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<Page<CondominiumDTO>> listPaginationCondominium(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sort) {
        log.info("Listando condominios");
        return ResponseEntity.ok(webCondominiumService.listPaginationCondominium(PageRequest.of(page, size, Sort.by(sort))));
    }

    @GetMapping(value = "list")
    public ResponseEntity<List<CondominiumDTO>> findAll(@RequestParam(name = "sort", defaultValue = "name") String sort) {
        log.info("Listando condominios");
        return ResponseEntity.ok(webCondominiumService.findAll(Sort.by(sort)));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody CondominiumDTO condominiumDTO) {
        if (webCondominiumService.save(condominiumDTO) == null) {
            return ResponseEntity.ok(messageSource.getMessage("TEXT_ERROR_INSERT_CONDOMINIUM",
                    null,
                    LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_INSERT_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody CondominiumDTO condominiumDTO) {
        log.info("Atualizando registro");
        webCondominiumService.update(condominiumDTO);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_UPDATE_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<CondominiumDTO>> search(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                       @RequestParam(name = "name") String name) {
        log.info("Localizando condominios");
        return ResponseEntity.ok(webCondominiumService.searchCondominium(PageRequest.of(page, size), new CondominiumDTO(name.trim())));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name = "id") Integer ID) {
        webCondominiumService.delete(ID);
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_DELETED_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }
}
