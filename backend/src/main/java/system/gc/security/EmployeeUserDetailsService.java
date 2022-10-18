package system.gc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import system.gc.entities.Employee;
import system.gc.services.ServiceImpl.EmployeeService;

@Component
@Slf4j
public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Tentativa de autenticação username: " + username);
        if (username == null || username.isEmpty()) {
            log.warn("Username inválido!");
            throw new UsernameNotFoundException("Campo username precisa ser preenchido");
        }
        Employee employeeUser = employeeService.authentication(username);
        if (employeeUser == null) {
            log.warn("Username não corresponde a nenhum registro!");
            throw new UsernameNotFoundException("Registro não foi localizado");
        }
        log.info("Usuário localizado");
        return new EmployeeUserDetails(employeeUser);
    }
}
