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
import system.gc.security.Filter.JWTAuthenticationEntryPoint;
import system.gc.security.Filter.LesseeAuthenticationFilter;
import system.gc.security.LesseeUserDetailsService;

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
        this.lesseeProviderManager = new ProviderManager(GCSystemDaoAuthenticationManagerProvider.create(lesseeUserDetailsService, new DaoAuthenticationProvider(), new BCryptPasswordEncoder()));
    }

    @Bean
    public SecurityFilterChain securityFilterChainLessee(HttpSecurity httpSecurity) throws Exception {
        log.info("Security filter lessee");
        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login/lessees").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        return httpSecurity.build();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter lesseeUsernamePasswordAuthenticationFilter() {
        return defaultInitUsernamePasswordAuthenticationFilter.init("/login/lessees",
                new LesseeAuthenticationFilter(),
                lesseeProviderManager);
    }
}
