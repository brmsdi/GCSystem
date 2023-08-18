package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Lessee;
import system.gc.services.web.AuthenticateEntity;
import system.gc.services.web.ChangePasswordEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface LesseeRepository extends JpaRepository<Lessee, Integer>, AuthenticateEntity<Lessee>, ChangePasswordEntity<Lessee> {

    @Query("SELECT lessee FROM Lessee lessee " +
            "JOIN FETCH lessee.status " +
            "JOIN FETCH lessee.role WHERE lessee IN :lessees")
    List<Lessee> loadLazyLessees(List<Lessee> lessees);

    @Query("SELECT lessee FROM Lessee lessee WHERE lessee.cpf LIKE :cpf")
    Optional<Lessee> findByCPF(String cpf);

    @Query("SELECT lessee FROM Lessee lessee WHERE lessee.cpf LIKE :cpf")
    Page<Lessee> findByCPF(Pageable pageable, String cpf);

    @Override
    @Query("SELECT lessee FROM Lessee lessee " +
            "JOIN FETCH lessee.status " +
            "JOIN FETCH lessee.role " +
            "WHERE lessee.cpf LIKE :cpf")
    Lessee getAuthenticationByCPF(String cpf);

    @Override
    @Query("SELECT lessee FROM Lessee lessee WHERE lessee.email LIKE :email")
    Optional<Lessee> findByEMAIL(String email);

    @Override
    @Query("SELECT lessee FROM Lessee lessee " +
            "JOIN FETCH lessee.logChangePassword logChangePassword " +
            "JOIN FETCH logChangePassword.status status " +
            "WHERE lessee.id = :ID AND status.id = :statusID")
    Optional<Lessee> checkIfThereISAnOpenRequest(Integer ID, Integer statusID);

    @Override
    @Query("SELECT lessee FROM Lessee lessee " +
            "JOIN FETCH lessee.logChangePassword logChangePassword " +
            "JOIN FETCH logChangePassword.status status " +
            "WHERE lessee.email LIKE :email " +
            "AND logChangePassword.id = :ID " +
            "AND logChangePassword.code LIKE :code " +
            "AND status.id = :statusID")
    Optional<Lessee> findRecordToChangePassword(String email, Integer ID, String code, Integer statusID);

}
