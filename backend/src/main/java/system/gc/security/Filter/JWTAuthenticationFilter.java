package system.gc.security.Filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import system.gc.dtos.EmployeeDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.security.EmployeeUserDetails;
import system.gc.security.LesseeUserDetails;
import system.gc.security.token.JWTService;
import system.gc.services.ServiceImpl.EmployeeService;
import system.gc.services.ServiceImpl.LesseeService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LesseeService lesseeService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Filtro JWTAuthentication inicializado");

        String token = getTokenFromHeader(request);
        if(token == null || !token.startsWith("Bearer")) {
            log.info("Agent sem token de acesso");
            chain.doFilter(request, response);
            return;
        }

        try {
            token = clearTypeToken(token);
            DecodedJWT decodedJWT = JWTService.isValid(token);
            SecurityContextHolder.getContext().setAuthentication(getUsernamePasswordAuthenticationToken(decodedJWT));
            response.setStatus(HttpServletResponse.SC_GONE);
            chain.doFilter(request, response);
        } catch (Exception exception)
        {
            log.error("Token de acesso invalido!");
            log.error(exception.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    /**
     *
     * @param request Requisição
     * @return Token existente na requisição
     */
    private String getTokenFromHeader(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token;
    }

    /**
     * Entrada: Bearer: 12345
     * Saída: 12345
     * @param token Token vindo da requisição
     * @return 'String' Recupera somente o token vindo na requisição.
     */
    private String clearTypeToken(String token)
    {
        return token.substring(7);
    }

    /**
     *
     * @param decodedJWT JWT DECODIFICADO
     * @return UsernamePasswordAuthenticationToken Autentica o usuário no sistema
     */
   private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(DecodedJWT decodedJWT) throws Exception{
       final String TYPE = decodedJWT.getClaim("TYPE").asString();
       final String USERNAME = decodedJWT.getClaim("USERNAME").asString();
        if(TYPE.equals(System.getenv("TYPE_1"))) {
            EmployeeDTO employeeDTO = employeeService.authentication(USERNAME);
            if(employeeDTO == null) {
                throw new Exception("O usuário não foi localizado (Employee)");
            }
            log.info("Usuário lozalizado. (Employee)");
            return create(new EmployeeUserDetails(employeeDTO));

        } else if(TYPE.equals(System.getenv("TYPE_2"))) {
            LesseeDTO lesseeDTO = lesseeService.authentication(USERNAME);
            if(lesseeDTO == null) {
                throw new Exception("O usuário não foi localizado (Lessee)");
            }
            log.info("Usuário lozalizado. (Lessee)");
            return create(new LesseeUserDetails(lesseeDTO));
        }
        throw new Exception("Token não corresponde a nenhum tipo de autenticação");
   }

   private UsernamePasswordAuthenticationToken create(UserDetails userDetails) {
       return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
               userDetails.getPassword(),
               userDetails.getAuthorities());
   }
}
