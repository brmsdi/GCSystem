package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Employee;
import system.gc.services.AuthenticateEntity;
import system.gc.services.ChangePasswordEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, AuthenticateEntity<Employee>, ChangePasswordEntity<Employee> {
    @Query("SELECT employee FROM Employee employee " +
            "JOIN FETCH employee.role " +
            "JOIN FETCH employee.status " +
            "WHERE employee IN :employees")
    List<Employee> loadLazyEmployees(List<Employee> employees);

    @Query("SELECT employee FROM Employee employee WHERE employee.cpf LIKE :cpf")
    Optional<Employee> findByCPF(String cpf);

    @Query("SELECT employee FROM Employee employee WHERE employee.cpf LIKE :cpf")
    Page<Employee> findByCPF(Pageable pageable, String cpf);

    @Override
    @Query("SELECT employee FROM Employee employee " +
            "JOIN FETCH employee.role " +
            "JOIN FETCH employee.status " +
            "WHERE employee.cpf LIKE :cpf")
    Employee getAuthenticationByCPF(String cpf);

    @Override
    @Query("SELECT employee FROM Employee employee WHERE employee.email LIKE :email")
    Optional<Employee> findByEMAIL(String email);

    @Override
    @Query("SELECT employee FROM Employee employee " +
            "JOIN FETCH employee.logChangePassword logChangePassword " +
            "JOIN FETCH logChangePassword.status status " +
            "WHERE employee.id = :ID AND status.id = :statusID")
    Optional<Employee> checkIfThereISAnOpenRequest(Integer ID, Integer statusID);

    @Override
    @Query("SELECT employee FROM Employee employee " +
            "JOIN FETCH employee.logChangePassword logChangePassword " +
            "JOIN FETCH logChangePassword.status status " +
            "WHERE employee.email LIKE :email " +
            "AND logChangePassword.id = :ID " +
            "AND logChangePassword.code LIKE :code " +
            "AND status.id = :statusID")
    Optional<Employee> findRecordToChangePassword(String email, Integer ID, String code, Integer statusID);

}
