package system.gc.security.Filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
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
import static system.gc.utils.RouteUtils.getAllPublicRoutesURI;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Campo utilizado para buscar funcionários no banco de dados, utilizando o username contido no token.
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * Campo utilizado para buscar funcionários no banco de dados, utilizando o username contido no token.
     */
    @Autowired
    private LesseeService lesseeService;

    /**
     * <p>Este método executa em todas as requisições para verificar se o token é valido.</p>
     * <p>Se a requisição for para um endpoint público, o token não será verificado e a requisição seguirá para o próximo filtro.</p>
     * <p>Se não houver token não requisição, não será realizada a verificação e a requisição seguirá para o próximo filtro.</p>
     * <p>Se o token for valido, o usuário será autenticado no sistema e a requisição seguirá para o próximo filtro.</p>
     * @param request - Padrão do Spring
     * @param response - Padrão do Spring
     * @param chain - Padrão do Spring
     * @throws ServletException - Padrão do Spring
     * @throws IOException - Padrão do Spring
     * @throws JWTVerificationException - Esta exceção será lançada se o token não for valido.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("Filtro JWTAuthentication inicializado");
        //Endpoints públicos - Não necessitam de token na requisição
        if (getAllPublicRoutesURI().contains(request.getRequestURI()))
        {
            log.info("Rota pública");
            chain.doFilter(request, response);
            return;
        }
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
            chain.doFilter(request, response);
        } catch (JWTVerificationException jwtVerificationException) {
            log.error("Token de acesso invalido!");
            log.error(jwtVerificationException.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            jwtVerificationException.printStackTrace();
        }
    }

    /**
     * <p>Este método retira da requisição o token de autorização se houver.</p>
     * @param request Requisição
     * @return String - Token existente na requisição
     */
    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token;
    }

    /**
     * <p>Este método retira o Bearer do token e retorna apenas o token.</p>
     * @param token Token vindo da requisição
     * @return String - Recupera somente o token vindo na requisição
     */
    private String clearTypeToken(String token) {
        return token.substring(7);
    }

    /**
     * <p>Este método recupera o username que está no token e busca no banco de dados da aplicação o usuário correspondente.</p>
     * @param decodedJWT JWT DECODIFICADO
     * @return UsernamePasswordAuthenticationToken Autentica o usuário no sistema
     */
    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(DecodedJWT decodedJWT) {
        final String TYPE = decodedJWT.getClaim("TYPE").asString();
        final String USERNAME = decodedJWT.getClaim("USERNAME").asString();
        if (TYPE.equals(System.getenv("TYPE_1"))) {
            Employee employee = employeeService.authentication(USERNAME);
            if (employee == null) {
                throw new BadCredentialsException("O usuário não foi localizado (Employee)");
            }
            log.info("Usuário localizado. (Employee)");
            return createV2(new EmployeeUserDetails(employee));

        } else if (TYPE.equals(System.getenv("TYPE_2"))) {
            Lessee lessee = lesseeService.authentication(USERNAME);
            if (lessee == null) {
                throw new BadCredentialsException("O usuário não foi localizado (Lessee)");
            }
            log.info("Usuário localizado. (Lessee)");
            return create(new LesseeUserDetails(lessee));
        }
        throw new BadCredentialsException("Token não corresponde a nenhum tipo de autenticação");
    }

    /**
     * <p>Este método cria o UsernamePasswordAuthenticationToken, necessário para autenticar o usuário.</p>
     * @param userDetails - Informações do usuário identificado
     * @return UsernamePasswordAuthenticationToken - com username, password e autoridades para ser autenticado no sistema.
     */
    private UsernamePasswordAuthenticationToken create(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    /**
     * <p>Este método cria o UsernamePasswordAuthenticationToken, necessário para autenticar o usuário.</p>
     * @param userDetails - Informações do usuário identificado
     * @return UsernamePasswordAuthenticationToken - com username, password, autoridades e o userDetails para ser autenticado no sistema.
     */
    private UsernamePasswordAuthenticationToken createV2(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(userDetails);
        return usernamePasswordAuthenticationToken;
    }
}
