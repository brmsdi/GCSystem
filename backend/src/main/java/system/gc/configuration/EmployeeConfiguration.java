package system.gc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import system.gc.security.EmployeeUserDetailsService;
import system.gc.security.Filter.EmployeeAuthenticationFilter;
import system.gc.security.Filter.JWTAuthenticationEntryPoint;
import system.gc.security.Filter.JWTAuthenticationFilter;

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
        this.employeeProviderManager = new ProviderManager(GCSystemDaoAuthenticationManagerProvider.create(employeeUserDetailsService, new DaoAuthenticationProvider(), new BCryptPasswordEncoder()));
    }

    @Bean
    public SecurityFilterChain securityFilterChainEmployee(HttpSecurity httpSecurity) throws Exception {
        log.info("Security filter employees");
        httpSecurity
               // .addFilterBefore(jwtAuthenticationFilter2(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login/employees").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        return httpSecurity.build();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter employeeUsernamePasswordAuthenticationFilter() {
        return defaultInitUsernamePasswordAuthenticationFilter.init("/login/employees",
                new EmployeeAuthenticationFilter(),
                employeeProviderManager);
    }
}
