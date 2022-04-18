package system.gc.services;

import java.util.Optional;

public interface ChangePasswordEntity<T> {
    Optional<T> findByEMAIL(String email);

    Optional<T> checkIfThereISAnOpenRequest(Integer ID, Integer statusID);

    Optional<T> findRecordToChangePassword(String email, Integer ID, String code, Integer statusID);
}
