package system.gc.configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import system.gc.configuration.generics.InitializeAuthenticationProvider;

public class InitializeDaoAuthenticationProvider implements InitializeAuthenticationProvider<DaoAuthenticationProvider> {
    private DaoAuthenticationProvider daoAuthenticationProvider;
    private InitializeDaoAuthenticationProvider(){}
    public static InitializeDaoAuthenticationProvider initialize(UserDetailsService userDetailsService,
                                                                 PasswordEncoder passwordEncoder) {
        InitializeDaoAuthenticationProvider dao = new InitializeDaoAuthenticationProvider();
        dao.daoAuthenticationProvider = new DaoAuthenticationProvider();
        dao.daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        dao.daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return dao;
    }
    @Override
    public DaoAuthenticationProvider create() {
        return daoAuthenticationProvider;
    }
}