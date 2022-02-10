package system.gc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import system.gc.security.EmployeeUserDetailsService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment environment;

    @Autowired
    EmployeeUserDetailsService employeeUserDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("Security Configuration");
        if(Arrays.stream(environment.getActiveProfiles()).toList().contains("test") ) {
            log.info("Perfil de teste ativado");
            httpSecurity.headers().frameOptions().disable();
        }
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        log.info("Configurando autenticação");
        return new ProviderManager(initProvider(employeeUserDetailsService));
    }

    private DaoAuthenticationProvider initProvider(UserDetailsService userDetailsService)
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
        jwtUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return jwtUsernamePasswordAuthenticationFilter;
    }
}