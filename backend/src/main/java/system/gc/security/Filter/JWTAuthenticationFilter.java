package system.gc.security.Filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import system.gc.dtos.EmployeeDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.entities.Employee;
import system.gc.entities.Lessee;
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
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LesseeService lesseeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("Filtro JWTAuthentication inicializado");

        String token = getTokenFromHeader(request);
        if (token == null || !token.startsWith("Bearer")) {
            log.info("Agent sem token de acesso");
            chain.doFilter(request, response);
            return;
        }
        log.info("Agent com token de acesso");
        try {
            token = clearTypeToken(token);
            DecodedJWT decodedJWT = JWTService.isValid(token);
            SecurityContextHolder.getContext().setAuthentication(getUsernamePasswordAuthenticationToken(decodedJWT));
            response.setStatus(HttpServletResponse.SC_GONE);
            chain.doFilter(request, response);
        } catch (JWTVerificationException jwtVerificationException) {
            log.error("Token de acesso invalido!");
            log.error(jwtVerificationException.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            jwtVerificationException.printStackTrace();
        }
    }

    /**
     * @param request Requisi????o
     * @return Token existente na requisi????o
     */
    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token;
    }

    /**
     * Entrada: Bearer: 12345
     * Sa??da: 12345
     *
     * @param token Token vindo da requisi????o
     * @return 'String' Recupera somente o token vindo na requisi????o.
     */
    private String clearTypeToken(String token) {
        return token.substring(7);
    }

    /**
     * @param decodedJWT JWT DECODIFICADO
     * @return UsernamePasswordAuthenticationToken Autentica o usu??rio no sistema
     */
    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(DecodedJWT decodedJWT) {
        final String TYPE = decodedJWT.getClaim("TYPE").asString();
        final String USERNAME = decodedJWT.getClaim("USERNAME").asString();
        if (TYPE.equals(System.getenv("TYPE_1"))) {
            Employee employee = employeeService.authentication(USERNAME);
            if (employee == null) {
                throw new BadCredentialsException("O usu??rio n??o foi localizado (Employee)");
            }
            log.info("Usu??rio localizado. (Employee)");
            return createV2(new EmployeeUserDetails(employee));

        } else if (TYPE.equals(System.getenv("TYPE_2"))) {
            Lessee lessee = lesseeService.authentication(USERNAME);
            if (lessee == null) {
                throw new BadCredentialsException("O usu??rio n??o foi localizado (Lessee)");
            }
            log.info("Usu??rio localizado. (Lessee)");
            return create(new LesseeUserDetails(lessee));
        }
        throw new BadCredentialsException("Token n??o corresponde a nenhum tipo de autentica????o");
    }

    private UsernamePasswordAuthenticationToken create(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    private UsernamePasswordAuthenticationToken createV2(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(userDetails);
        return usernamePasswordAuthenticationToken;
    }
}
