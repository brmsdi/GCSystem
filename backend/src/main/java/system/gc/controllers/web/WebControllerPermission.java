package system.gc.controllers.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
public interface WebControllerPermission {

    /**
     * <p>Verificar se o usuário autenticado tem permissão para acessar o endpoint atual. A requisição só chegará a este endpoint se o usuário autenticado tiver autorização.</p>
     * @return status 200 ok
     */
    @GetMapping(value = "permission")
    default ResponseEntity<String> hasPermission()
    {
        return ResponseEntity.ok("ok");
    }
}
