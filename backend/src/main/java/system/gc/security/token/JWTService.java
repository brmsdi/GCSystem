package system.gc.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j
public class JWTService {

    /**
     * @param claims Parametros para compor a chave autenticada
     * @param TIME_TOKEN        Validade do token
     * @return 'String'         Token criado
     */
    public static String createTokenJWT(final Map<String, String> claims, Long TIME_TOKEN) {
        log.info("Criando token");
        JWTCreator.Builder builder = JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TIME_TOKEN));
        claims.forEach(builder::withClaim);
        return builder.sign(Algorithm.HMAC256(System.getenv("PRIVATE_KEY_TOKEN")));
        /*
        return JWT
                .create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TextUtils.TIME_TOKE_EXPIRATION))
                .withClaim("USERNAME", authentication.getName())
                .withClaim("TYPE", TYPE)
                .sign(Algorithm.HMAC256(System.getenv("PRIVATE_KEY_TOKEN"))); */
    }

    public static DecodedJWT isValid(String token) throws JWTVerificationException {
        return JWT
                .require(Algorithm.HMAC256(System.getenv("PRIVATE_KEY_TOKEN")))
                .build()
                .verify(token);
    }
}
