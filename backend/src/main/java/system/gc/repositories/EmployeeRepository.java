package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
