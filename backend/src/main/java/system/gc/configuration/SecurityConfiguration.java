package system.gc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import system.gc.security.EmployeeUserDetailsService;
import system.gc.security.Filter.*;
import system.gc.security.LesseeUserDetailsService;
import system.gc.utils.TextUtils;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;
    @Autowired
    JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final ProviderManager employeeProviderManager;

    private final ProviderManager lesseeProviderManager;

    private final DefaultInitUsernamePasswordAuthenticationFilter defaultInitUsernamePasswordAuthenticationFilter = new DefaultInitUsernamePasswordAuthenticationFilter();

    protected SecurityConfiguration(EmployeeUserDetailsService employeeUserDetailsService, LesseeUserDetailsService lesseeUserDetailsService) {
        this.employeeProviderManager = new ProviderManager(GCSystemDaoAuthenticationManagerProvider.create(employeeUserDetailsService, new DaoAuthenticationProvider(), new BCryptPasswordEncoder()));
        this.lesseeProviderManager = new ProviderManager(GCSystemDaoAuthenticationManagerProvider.create(lesseeUserDetailsService, new DaoAuthenticationProvider(), new BCryptPasswordEncoder()));
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("Security Configuration");
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.csrf().disable();
        httpSecurity
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .addFilterBefore(jwtAuthenticationFilter2(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/employees/login").permitAll()
                .antMatchers(HttpMethod.POST, "/lessees/login").permitAll()
                .antMatchers(HttpMethod.POST, "/change-password/request-code").permitAll()
                .antMatchers(HttpMethod.POST, "/change-password/receive-code").permitAll()
                .antMatchers(HttpMethod.POST, "/change-password/change").permitAll()
                .antMatchers("/employees/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR)
                .antMatchers("/lessees/search").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT, TextUtils.ROLE_COUNTER)
                .antMatchers("/lessees/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .antMatchers("/debts/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_COUNTER)
                .antMatchers("/condominiums/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .antMatchers("/contracts/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .antMatchers("/repair-requests/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .antMatchers("/order-services/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }

    @Bean
    public UsernamePasswordAuthenticationFilter employeeUsernamePasswordAuthenticationFilter() {
        return defaultInitUsernamePasswordAuthenticationFilter.init("/employees/login",
                new EmployeeAuthenticationFilter(),
                employeeProviderManager);
    }

    @Bean
    public UsernamePasswordAuthenticationFilter lesseeUsernamePasswordAuthenticationFilter() {
        return defaultInitUsernamePasswordAuthenticationFilter.init("/lessees/login",
                new LesseeAuthenticationFilter(),
                lesseeProviderManager);
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter2() {
        return new JWTAuthenticationFilter();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        log.info("configurando cors");
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "UPDATE", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedOrigins(
                List.of(Objects.requireNonNull(environment.getProperty("ORIGINV1")),
                        Objects.requireNonNull(environment.getProperty("ORIGINV2")),
                        Objects.requireNonNull(environment.getProperty("ORIGINV3")),
                        Objects.requireNonNull(environment.getProperty("ORIGINV4"))));
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }
}