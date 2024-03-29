package system.gc.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Slf4j
public class JWTService {

    /**
     * @param claims Parametros para compor a chave autenticada
     * @param TIME_TOKEN Validade do token
     * @return String - Token gerado
     */
    public static String createTokenJWT(final Map<String, String> claims, Long TIME_TOKEN) {
        log.info("Criando token");
        JWTCreator.Builder builder = JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TIME_TOKEN));
        claims.forEach(builder::withClaim);
        return builder.sign(Algorithm.HMAC256(System.getenv("PRIVATE_KEY_TOKEN")));
    }

    /**
     * @param claims Parametros para compor a chave autenticada
     * @param localDateTimeNow Data e hora inicial
     * @param TIME Validade do token. Quantidade de horas para o token expirar
     * @return String - Token gerado
     */
    public static String createTokenJWT(final Map<String, String> claims, LocalDateTime localDateTimeNow, final int TIME) {
        log.info("Criando token");
        JWTCreator.Builder builder = JWT.create()
                .withIssuedAt(Date.from(localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(localDateTimeNow.plusHours(TIME).atZone(ZoneId.systemDefault()).toInstant()));
        claims.forEach(builder::withClaim);
        return builder.sign(Algorithm.HMAC256(System.getenv("PRIVATE_KEY_TOKEN")));
    }

    /**
     * <p>Este método verifica se o token é valido.</p>
     * @param token Token submetido a teste
     * @return DecodedJWT JWT decodificado
     * @throws JWTVerificationException - Lança esta exceção se o token não for valido
     */
    public static DecodedJWT isValid(String token) throws JWTVerificationException {
        return JWT
                .require(Algorithm.HMAC256(System.getenv("PRIVATE_KEY_TOKEN")))
                .build()
                .verify(token);
    }
}
