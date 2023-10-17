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
import system.gc.utils.*;

import java.util.*;

import static system.gc.utils.RoutesPublicEmployee.LOGIN_EMPLOYEES;
import static system.gc.utils.RoutesPublicLessee.LOGIN_LESSEES;
import static system.gc.utils.TextUtils.API_V1_MOBILE;
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
     * <p>Para acesso aos endpoints mobile, não será necessário o cors.</p>
     * @return CorsConfigurationSource Cross-origin resource sharing
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        log.info("Configurando cors");
        String[] origins = Objects.requireNonNull(environment.getProperty("ORIGINS")).split(",");
        List<String> methods = List.of("GET", "POST", "UPDATE", "PUT", "DELETE", "OPTIONS");
        // CORS WEB
        CorsConfiguration webcorsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        webcorsConfiguration.setAllowedMethods(methods);
        webcorsConfiguration.setAllowedOrigins(Arrays.stream(origins).toList());
        //CORS MOBILE
        CorsConfiguration mobileCorsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        mobileCorsConfiguration.setAllowedOrigins(methods);
        mobileCorsConfiguration.setAllowedOrigins(List.of("*"));
        Map<String, CorsConfiguration> corsConfigurationMap = Map.of(
                API_V1_WEB.concat("/**"),
                webcorsConfiguration,
                LOGIN_EMPLOYEES.getRoute().url(),
                webcorsConfiguration,
                LOGIN_LESSEES.getRoute().url(),
                webcorsConfiguration,
                API_V1_MOBILE.concat("/**"),
                mobileCorsConfiguration
        );
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        return getUrlBasedCorsConfigurationSource(urlBasedCorsConfigurationSource, corsConfigurationMap);
    }

    private static UrlBasedCorsConfigurationSource getUrlBasedCorsConfigurationSource(UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource, Map<String, CorsConfiguration> corsConfigurationMap) {
        corsConfigurationMap.forEach(urlBasedCorsConfigurationSource::registerCorsConfiguration);
        urlBasedCorsConfigurationSource.setCorsConfigurations(corsConfigurationMap);
        return urlBasedCorsConfigurationSource;
    }
}