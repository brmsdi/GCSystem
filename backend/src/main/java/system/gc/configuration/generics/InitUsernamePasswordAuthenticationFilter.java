package system.gc.configuration.generics;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
public interface InitUsernamePasswordAuthenticationFilter {
    /**
     * @param url URL para tentativa de autenticação específica
     * @param usernamePasswordAuthenticationFilter Filtro para processar a tentativa de autenticação
     * @param authenticationManager Provedor de autenticação
     * @return UsernamePasswordAuthenticationFilter Filtro funcional para processar as autenticações
     */
    UsernamePasswordAuthenticationFilter init(String url,
    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter,
    AuthenticationManager authenticationManager);
}
