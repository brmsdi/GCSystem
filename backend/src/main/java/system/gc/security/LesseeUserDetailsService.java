package system.gc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import system.gc.dtos.EmployeeDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.services.ServiceImpl.LesseeService;

@Component
@Slf4j
public class LesseeUserDetailsService implements UserDetailsService {

    @Autowired
    private LesseeService lesseeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Tentativa de autenticação username: " + username);

        if (username == null || username.isEmpty()) {
            log.warn("Username inválido!");
            throw new UsernameNotFoundException("Campo username precisa ser preenchido");
        }
        LesseeDTO lesseeUser = lesseeService.authentication(username);

        if (lesseeUser == null) {
            log.warn("Username não corresponde a nenhum registro!");
            throw new UsernameNotFoundException("Registro não foi localizado");
        }

        return new LesseeUserDetails(lesseeUser);
    }
}
