package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Employee;
import system.gc.services.AuthenticateEntityByCPF;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, AuthenticateEntityByCPF<Employee> {
    @Query("SELECT employee FROM Employee employee " +
            "JOIN FETCH employee.specialties " +
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
    Employee getAuthentication(String cpf);
}
