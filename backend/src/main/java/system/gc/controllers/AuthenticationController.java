package system.gc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import system.gc.security.UserAuthenticatedView;
import system.gc.security.UserDetailsConvert;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface AuthenticationController {
    /**
     * Validar token enviado na requisição. A requisição só chegará a este endpoint se o token da requisição for valido.
     * @return UserAuthenticatedView - Usuário identificado no sistema
     */
    @GetMapping("token")
    default ResponseEntity<UserAuthenticatedView> validateToken() {
        UserDetailsConvert user = (UserDetailsConvert) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return ResponseEntity.ok(user.getNameAndRoleUser());
    }
}
