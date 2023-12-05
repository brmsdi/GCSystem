package system.gc.configuration.generics;

import org.springframework.security.authentication.AuthenticationProvider;
/**
 * @author Wisley Bruno Marques França
 * @param <AP> AuthenticationProvider
 * @since 1.3
 * @version 1.3
 */
public interface InitializeAuthenticationProvider<AP extends AuthenticationProvider> {
    /**
     * @return AuthenticationProvider provedor funcional com os parâmetros configurados*/
    AP create();
}
