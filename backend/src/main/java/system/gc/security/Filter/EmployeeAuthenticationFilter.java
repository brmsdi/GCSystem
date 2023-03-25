package system.gc.security.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import system.gc.security.token.CreateTokenSuccessFulAuthentication;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Slf4j
public class EmployeeAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements CreateTokenSuccessFulAuthentication {

    /**
     * <p>Este método insere o token no corpo da resposta da requisição.</p>
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Usuário autenticado.");
        try {
            Map<String, String> params = new HashMap<>();
            params.put("USERNAME", authResult.getName());
            params.put("TYPE", System.getenv("TYPE_1"));
            createTokenSuccessFulAuthentication(response, params);
        } catch (Exception e) {
            log.error("Erro ao criar token");
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
