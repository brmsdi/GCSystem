package system.gc.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import system.gc.utils.TextUtils;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JWTService {

    /**
     *
     * @param authentication    Manager utilizado para autenticar o usuário
     * @param TYPE              Tipo da autenticação
     * @return 'String'         Token criado para autenticação
     * @throws Exception        Se o token não for criado
     */
    public static String createTokenJWT(Authentication authentication, final String TYPE) throws Exception {
        log.info("Criando token valido por " + TimeUnit.HOURS.convert(TextUtils.TIME_TOKE_EXPIRATION, TimeUnit.MILLISECONDS) + " horas");
        return JWT
                .create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TextUtils.TIME_TOKE_EXPIRATION))
                .withClaim("USERNAME", authentication.getName())
                .withClaim("TYPE", TYPE)
                .sign(Algorithm.HMAC256(System.getenv("PRIVATE_KEY_TOKEN")));
    }

    public static DecodedJWT isValid(String token) throws JWTVerificationException {
        return JWT
                .require(Algorithm.HMAC256(System.getenv("PRIVATE_KEY_TOKEN")))
                .build()
                .verify(token);

    }

}
