package system.gc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import system.gc.security.Filter.JWTAuthenticationEntryPoint;
import system.gc.security.Filter.JWTAuthenticationFilter;
import system.gc.utils.TextUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSecurity
@Slf4j
public class DefaultConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        log.info("Security Configuration");
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.csrf().disable();
        httpSecurity
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/change-password/request-code").permitAll()
                .antMatchers(HttpMethod.POST, "/change-password/receive-code").permitAll()
                .antMatchers(HttpMethod.POST, "/change-password/change").permitAll()
                .antMatchers("/employees/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR)
                .antMatchers("/lessees/search").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT, TextUtils.ROLE_COUNTER)
                .antMatchers("/lessees/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .antMatchers("/v1/pix/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_COUNTER)
                .antMatchers("/condominiums/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .antMatchers("/contracts/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .antMatchers("/repair-requests/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .antMatchers("/order-services/**").hasAnyRole(TextUtils.ROLE_ADMINISTRATOR, TextUtils.ROLE_ADMINISTRATIVE_ASSISTANT)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        log.info("configurando cors");
        String[] origins = Objects.requireNonNull(environment.getProperty("ORIGINS")).split(",");
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "UPDATE", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedOrigins(Arrays.stream(origins).toList());
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }
}
