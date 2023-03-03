package system.gc.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import system.gc.exceptionsAdvice.exceptions.CodeChangePasswordInvalidException;
import system.gc.entities.LogChangePassword;
import system.gc.entities.Status;
import system.gc.security.token.JWTService;
import system.gc.services.ServiceImpl.LogPasswordCodeService;
import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

/**
 * @author Wisley Bruno Marques França
 * @param <E> Entity
 * @param <REPOSITORY> Entity repository
 */
public interface ChangePasswordInterface<E, REPOSITORY extends ChangePasswordEntity<E>> {

     default Optional<E> checkIfThereISAnOpenRequest(Integer ID, REPOSITORY repository, Integer statusID) {
          return repository.checkIfThereISAnOpenRequest(ID, statusID);
     }

     default String generateCode() {
          Random random = new Random();
          StringBuilder code = new StringBuilder();
          for (int index = 0; index < 3; index++) {
               int currentNumber = random.nextInt(10, 99);
               code.append(currentNumber);
          }
          return new String(code);
     }

     default LogChangePassword startProcess(E e, Status status, LogPasswordCodeService logPasswordCodeService) {
          String code = generateCode();
          LogChangePassword logChangePassword = new LogChangePassword(e, code, status, new Date(), Short.parseShort("0"));
          return logPasswordCodeService.save(logChangePassword);
     }

     default Optional<E> verifyTokenForChangePassword(String token, REPOSITORY repository, Integer statusID) {
          try {
               DecodedJWT tokenDecoded = JWTService.isValid(token);
               String email = tokenDecoded.getClaim("EMAIL").asString();
               Integer ID = Integer.valueOf(tokenDecoded.getClaim("ID").asString());
               String code = tokenDecoded.getClaim("CODE").asString();
               return repository.findRecordToChangePassword(email, ID, code, statusID);
          } catch (JWTVerificationException | NumberFormatException exception) {
               throw new CodeChangePasswordInvalidException(exception.getMessage());
          }
     }

     default E verifyEmail(String email, REPOSITORY repository) {
          return repository.findByEMAIL(email).orElseThrow(() -> new EntityNotFoundException("Nenhum registro encontrado com esse E-mail"));
     }
}
