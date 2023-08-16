package system.gc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import system.gc.configuration.generics.InitUsernamePasswordAuthenticationFilter;
import system.gc.security.Filter.LesseeAuthenticationFilter;
import system.gc.security.LesseeUserDetailsService;

import static system.gc.utils.RoutesPublicLessee.LOGIN_LESSEES;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Configuration
@Order(2)
@Slf4j
public class LesseeConfiguration {

    private final ProviderManager lesseeProviderManager;
    private final DefaultInitUsernamePasswordAuthenticationFilter defaultInitUsernamePasswordAuthenticationFilter = new DefaultInitUsernamePasswordAuthenticationFilter();

    @Autowired
    protected LesseeConfiguration(LesseeUserDetailsService lesseeUserDetailsService) {
        this.lesseeProviderManager = new ProviderManager(InitializeDaoAuthenticationProvider.initialize(lesseeUserDetailsService, new BCryptPasswordEncoder()).create());
    }

    /**
     * <p>Classe responsável pela autenticação dos locatários no sistema.</p>
     * @return UsernamePasswordAuthenticationFilter configurado com os parâmetros específicados
     * @see InitUsernamePasswordAuthenticationFilter
     */
    @Bean
    public UsernamePasswordAuthenticationFilter lesseeUsernamePasswordAuthenticationFilter() {
        return defaultInitUsernamePasswordAuthenticationFilter.init(LOGIN_LESSEES.getRoute().url(),
                new LesseeAuthenticationFilter(),
                lesseeProviderManager);
    }
}
