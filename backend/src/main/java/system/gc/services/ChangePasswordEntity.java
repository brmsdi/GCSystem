package system.gc.services;

import java.util.Optional;

public interface ChangePasswordEntity<T> {
    Optional<T> findByEMAIL(String email);

    Optional<T> CheckIfThereISAnOpenRequest(Integer ID, Integer statusID);
}
