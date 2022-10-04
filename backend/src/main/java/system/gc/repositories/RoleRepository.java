package system.gc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.gc.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {}