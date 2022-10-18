package system.gc.configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GCSystemDaoAuthenticationManagerProvider {
    
    public static DaoAuthenticationProvider create(UserDetailsService userDetailsService,
    DaoAuthenticationProvider daoAuthenticationProvider,
    BCryptPasswordEncoder bcryptPasswordEncoder) {
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bcryptPasswordEncoder);
        return daoAuthenticationProvider;
    }
}