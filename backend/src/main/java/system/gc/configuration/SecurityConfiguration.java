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
import system.gc.security.Filter.*;
import system.gc.security.LesseeUserDetailsService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    Environment environment;

    final EmployeeUserDetailsService employeeUserDetailsService;

    final LesseeUserDetailsService lesseeUserDetailsService;

    ProviderManager employeeProviderManager;

    ProviderManager lesseeProviderManager;

    protected SecurityConfiguration(EmployeeUserDetailsService employeeUserDetailsService, LesseeUserDetailsService lesseeUserDetailsService) {
        this.employeeUserDetailsService = employeeUserDetailsService;
        this.lesseeUserDetailsService = lesseeUserDetailsService;
        this.employeeProviderManager = new ProviderManager(employeeDaoAuthenticationManagerProvider(this.employeeUserDetailsService));
        this.lesseeProviderManager = new ProviderManager(lesseeDaoAuthenticationManagerProvider(this.lesseeUserDetailsService));
    }

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
                .addFilterBefore(jwtAuthenticationFilter2(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/employee/login").permitAll()
                .antMatchers(HttpMethod.POST, "/lessee/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }

    public DaoAuthenticationProvider employeeDaoAuthenticationManagerProvider(UserDetailsService userDetailsService)
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    private DaoAuthenticationProvider lesseeDaoAuthenticationManagerProvider(LesseeUserDetailsService lesseeUserDetailsService)
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(lesseeUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UsernamePasswordAuthenticationFilter employeeUsernamePasswordAuthenticationFilter() {
        return initUsernamePasswordAuthenticationFilter("/employee/login",
                new EmployeeAuthenticationFilter(),
                employeeProviderManager);
    }

    @Bean
    public UsernamePasswordAuthenticationFilter lesseeUsernamePasswordAuthenticationFilter() {
        return initUsernamePasswordAuthenticationFilter("/lessee/login",
                new LesseeAuthenticationFilter(),
                lesseeProviderManager);
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter2() {
        return new JWTAuthenticationFilter();
    }

    private UsernamePasswordAuthenticationFilter initUsernamePasswordAuthenticationFilter(String url,
                                                                                          UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter,
                                                                                          AuthenticationManager authenticationManager) {

        usernamePasswordAuthenticationFilter.setFilterProcessesUrl(url);
        usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return usernamePasswordAuthenticationFilter;

    }

}