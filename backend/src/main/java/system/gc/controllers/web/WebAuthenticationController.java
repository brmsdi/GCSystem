package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import system.gc.security.UserAuthenticatedView;
import system.gc.security.UserDetailsConvert;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@RestController
@Slf4j
@RequestMapping(value = API_V1_WEB + "/validate")
public class WebAuthenticationController {

    @Autowired
    private MessageSource messageSource;

    /**
     * Validar token enviado na requisição. A requisição só chegará a este endpoint se o token da requisição for valido.
     * @return UserAuthenticatedView - Usuário identificado no sistema
     */
    @GetMapping("token")
    public ResponseEntity<UserAuthenticatedView> validateToken() {
        log.info("Token valido");
        UserDetailsConvert user = (UserDetailsConvert) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return ResponseEntity.ok(user.getNameAndRoleUser());
    }
}