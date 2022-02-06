package system.gc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment environment;

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
                .antMatchers("/h2-console/**")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/employee")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/employee")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/role")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/specialty")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/status")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/condominium")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/condominium")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/lessee")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/contract")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/contract")
                .permitAll();

    }
}