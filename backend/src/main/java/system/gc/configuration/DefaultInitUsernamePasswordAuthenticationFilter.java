package system.gc.configuration;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import system.gc.configuration.generics.InitUsernamePasswordAuthenticationFilter;

public class DefaultInitUsernamePasswordAuthenticationFilter implements InitUsernamePasswordAuthenticationFilter {

    @Override
    public UsernamePasswordAuthenticationFilter init(String url,
                                              UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter,
                                              AuthenticationManager authenticationManager) {
        usernamePasswordAuthenticationFilter.setFilterProcessesUrl(url);
        usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return usernamePasswordAuthenticationFilter;
    }
}