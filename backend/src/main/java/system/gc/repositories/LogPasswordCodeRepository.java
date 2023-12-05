package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.LogChangePassword;

import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface LogPasswordCodeRepository extends JpaRepository<LogChangePassword, Integer> {
    @Query("SELECT obj FROM LogChangePassword obj " +
            "JOIN FETCH obj.employee employee " +
            "JOIN FETCH obj.status status WHERE employee.email LIKE :email AND status.id = :statusID")
    Optional<LogChangePassword> findPasswordChangeRequestEmployee(String email, Integer statusID);

    @Query("SELECT obj FROM LogChangePassword obj " +
            "JOIN FETCH obj.lessee lessee " +
            "JOIN FETCH obj.status status WHERE lessee.email LIKE :email AND status.id = :statusID")
    Optional<LogChangePassword> findPasswordChangeRequestLessee(String email, Integer statusID);

}
