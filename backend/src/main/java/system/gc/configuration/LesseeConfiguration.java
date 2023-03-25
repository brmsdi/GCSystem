package system.gc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import system.gc.configuration.generics.InitUsernamePasswordAuthenticationFilter;
import system.gc.security.Filter.JWTAuthenticationEntryPoint;
import system.gc.security.Filter.LesseeAuthenticationFilter;
import system.gc.security.LesseeUserDetailsService;
import system.gc.utils.Route;
import system.gc.utils.RoutesPublicLessee;
import java.util.Arrays;

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
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    protected LesseeConfiguration(LesseeUserDetailsService lesseeUserDetailsService) {
        this.lesseeProviderManager = new ProviderManager(InitializeDaoAuthenticationProvider.initialize(lesseeUserDetailsService, new BCryptPasswordEncoder()).create());
    }

    @Bean
    public SecurityFilterChain securityFilterChainLessee(HttpSecurity httpSecurity) throws Exception {
        log.info("Lessees security filter chain");
        //ADICIONA ROTAS PUBLICAS
        httpSecurity.authorizeRequests(expressionInterceptUrlRegistry -> Arrays.stream(RoutesPublicLessee.values()).forEach(routesPublicLessee -> {
            Route route = routesPublicLessee.getRoute();
            expressionInterceptUrlRegistry
                    .antMatchers(route.getHttpMethod(), route.getRoute()).permitAll();
        }));
        httpSecurity
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        return httpSecurity.build();
    }

    /**
     * <p>Classe responsável pela autenticação dos locatários no sistema.</p>
     * @return UsernamePasswordAuthenticationFilter configurado com os parâmetros específicados
     * @see InitUsernamePasswordAuthenticationFilter
     */
    @Bean
    public UsernamePasswordAuthenticationFilter lesseeUsernamePasswordAuthenticationFilter() {
        return defaultInitUsernamePasswordAuthenticationFilter.init("/login/lessees",
                new LesseeAuthenticationFilter(),
                lesseeProviderManager);
    }
}
