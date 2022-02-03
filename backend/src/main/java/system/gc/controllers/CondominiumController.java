package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.gc.dtos.CondominiumDTO;
import system.gc.services.CondominiumService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/condominium")
@Slf4j
public class CondominiumController {

    @Autowired
    private CondominiumService condominiumService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<Page<CondominiumDTO>> findAll(Pageable pageable) {
        log.info("Listando condominios");
        return ResponseEntity.ok(condominiumService.findAllPagination(pageable));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody CondominiumDTO condominiumDTO) {
        if(condominiumService.save(condominiumDTO) == null) {
            return ResponseEntity.ok(messageSource.getMessage("TEXT_ERROR_INSERT_CONDOMINIUM",
                    null,
                    LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok(messageSource.getMessage("TEXT_MSG_INSERT_SUCCESS",
                null,
                LocaleContextHolder.getLocale()));
    }

}
