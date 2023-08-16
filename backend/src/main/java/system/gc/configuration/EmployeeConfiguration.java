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
import system.gc.security.EmployeeUserDetailsService;
import system.gc.security.Filter.EmployeeAuthenticationFilter;

import static system.gc.utils.RoutesPublicEmployee.LOGIN_EMPLOYEES;

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
    protected EmployeeConfiguration(EmployeeUserDetailsService employeeUserDetailsService) {
        this.employeeProviderManager = new ProviderManager(InitializeDaoAuthenticationProvider.initialize(employeeUserDetailsService, new BCryptPasswordEncoder()).create());
    }

    /**
     * <p>Classe responsável pela autenticação dos funcionários no sistema.</p>
     * @return UsernamePasswordAuthenticationFilter Configurado com os parâmetros específicados
     * @see InitUsernamePasswordAuthenticationFilter
     */
    @Bean
    public UsernamePasswordAuthenticationFilter employeeUsernamePasswordAuthenticationFilter() {
        return defaultInitUsernamePasswordAuthenticationFilter.init(LOGIN_EMPLOYEES.getRoute().url(),
                new EmployeeAuthenticationFilter(),
                employeeProviderManager);
    }
}
