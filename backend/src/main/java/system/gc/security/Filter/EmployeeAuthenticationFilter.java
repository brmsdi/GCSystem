package system.gc.security.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import system.gc.dtos.TokenDTO;
import system.gc.security.token.JWTService;
import system.gc.utils.TextUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class EmployeeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Sucesso");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String token;
        try {
            token = JWTService.createTokenJWT(authResult, System.getenv("TYPE_1"));
            response.getWriter().print(TextUtils.GSON.toJson(TokenDTO.builder().type("Bearer").token(token).build()));
            response.setStatus(HttpServletResponse.SC_GONE);
        } catch (Exception e) {
            log.error("Erro ao criar token");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        //super.successfulAuthentication(request, response, chain, authResult);
    }
}
