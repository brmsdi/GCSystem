package system.gc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
import system.gc.utils.RoutePrivate;
import system.gc.utils.RouteUtils;
import system.gc.utils.RoutesPrivate;

import java.util.*;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

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
        log.info("Default security filter chain");
        String profile = Objects.requireNonNull(environment.getProperty("SPRING_PROFILES_ACTIVE"));
        if (profile.equals("test") || profile.equals("development")) {
            httpSecurity.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        }
        httpSecurity
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
                .authorizeRequests(expressionInterceptUrlRegistry -> {
                    RouteUtils.getAllPublicRoutes().forEach(route -> {
                        expressionInterceptUrlRegistry.antMatchers(route.httpMethod(), route.url()).permitAll();
                    });
                    Arrays.stream(RoutesPrivate.values()).forEach(routePrivate -> {
                                RoutePrivate item = routePrivate.getRoute();
                                expressionInterceptUrlRegistry.antMatchers(item.url()).hasAnyRole(item.roles());
                            }
                    );
                })
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return httpSecurity.build();
    }

    /**
     * <p>Este CorsConfigurationSource será configurado para permitir acesso somente das urls definidas na variável de ambiente 'ORIGINS'.</p>
     *
     * @return CorsConfigurationSource Cross-origin resource sharing
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        log.info("Configurando cors");
        String[] origins = Objects.requireNonNull(environment.getProperty("ORIGINS")).split(",");
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "UPDATE", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedOrigins(Arrays.stream(origins).toList());
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration(API_V1_WEB.concat("/**"), corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }
}
