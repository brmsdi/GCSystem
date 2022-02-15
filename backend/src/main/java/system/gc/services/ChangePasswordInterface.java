package system.gc.services;

import system.gc.entities.PasswordCode;
import system.gc.entities.Status;
import system.gc.services.ServiceImpl.PasswordCodeService;
import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

public interface ChangePasswordInterface<E, REPOSITORY extends ChangePasswordEntity<E>> {
     default E verifyEmail(String email, REPOSITORY repository) {
          return repository.findByEMAIL(email).orElseThrow(() -> new EntityNotFoundException("Nenhum registro encontrado com esse E-mail"));
     }

     default Optional<E> CheckIfThereISAnOpenRequest(Integer ID, REPOSITORY repository, Integer statusID) {
          return repository.CheckIfThereISAnOpenRequest(ID, statusID);
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

     default PasswordCode startProcess(E e, Status status, PasswordCodeService passwordCodeService) {
          String code = generateCode();
          PasswordCode passwordCode = new PasswordCode(e, code, status, new Date(), Short.parseShort("0"));
          return passwordCodeService.save(passwordCode);
     }

     default boolean sendEmail(PasswordCode passwordCode, String email) {
          System.out.println("Enviando E-mail");
          return true;
     }

}
