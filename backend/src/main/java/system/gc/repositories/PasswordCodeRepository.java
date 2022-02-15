package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.PasswordCode;

import java.util.Optional;

public interface PasswordCodeRepository extends JpaRepository<PasswordCode, Integer> {
    @Query("SELECT obj FROM PasswordCode obj " +
            "JOIN FETCH obj.employee employee " +
            "JOIN FETCH obj.status status WHERE employee.email LIKE :email AND status.id = :statusID")
    Optional<PasswordCode> findPasswordChangeRequestEmployee(String email, Integer statusID);

}
