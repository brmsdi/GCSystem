package system.gc.security.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import system.gc.dtos.TokenDTO;
import system.gc.security.token.CreateTokenSuccessFulAuthentication;
import system.gc.security.token.JWTService;
import system.gc.utils.TextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class EmployeeAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements CreateTokenSuccessFulAuthentication {

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Usuário autenticado.");
        try {
            createTokenSuccessFulAuthentication(response, authResult, System.getenv("TYPE_1"));
        } catch (Exception e) {
            log.error("Erro ao criar token");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        //super.successfulAuthentication(request, response, chain, authResult);
    }
}
