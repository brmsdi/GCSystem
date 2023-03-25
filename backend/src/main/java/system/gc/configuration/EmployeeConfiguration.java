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
import system.gc.security.EmployeeUserDetailsService;
import system.gc.security.Filter.EmployeeAuthenticationFilter;
import system.gc.security.Filter.JWTAuthenticationEntryPoint;
import system.gc.utils.Route;
import system.gc.utils.RoutesPublicEmployee;
import java.util.Arrays;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Configuration
@Order(1)
@Slf4j
public class EmployeeConfiguration {

    private final ProviderManager employeeProviderManager;
    private final DefaultInitUsernamePasswordAuthenticationFilter defaultInitUsernamePasswordAuthenticationFilter = new DefaultInitUsernamePasswordAuthenticationFilter();
    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    protected EmployeeConfiguration(EmployeeUserDetailsService employeeUserDetailsService) {
        this.employeeProviderManager = new ProviderManager(InitializeDaoAuthenticationProvider.initialize(employeeUserDetailsService, new BCryptPasswordEncoder()).create());
    }

    @Bean
    public SecurityFilterChain securityFilterChainEmployee(HttpSecurity httpSecurity) throws Exception {
        log.info("Employees security filter chain");
        //ADICIONA ROTAS PUBLICAS
        httpSecurity.authorizeRequests(expressionInterceptUrlRegistry ->
                Arrays.stream(RoutesPublicEmployee.values()).forEach(routesPublicEmployee -> {
                    Route route = routesPublicEmployee.getRoute();
                    expressionInterceptUrlRegistry
                            .antMatchers(route.getHttpMethod(), route.getRoute()).permitAll();
                }));
        httpSecurity
                .authorizeRequests()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        return httpSecurity.build();
    }

    /**
     * <p>Classe responsável pela autenticação dos funcionários no sistema.</p>
     * @return UsernamePasswordAuthenticationFilter Configurado com os parâmetros específicados
     * @see InitUsernamePasswordAuthenticationFilter
     */
    @Bean
    public UsernamePasswordAuthenticationFilter employeeUsernamePasswordAuthenticationFilter() {
        return defaultInitUsernamePasswordAuthenticationFilter.init("/login/employees",
                new EmployeeAuthenticationFilter(),
                employeeProviderManager);
    }
}
