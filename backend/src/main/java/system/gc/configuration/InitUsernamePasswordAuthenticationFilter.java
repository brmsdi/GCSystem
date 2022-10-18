package system.gc.configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public interface InitUsernamePasswordAuthenticationFilter {
    public default UsernamePasswordAuthenticationFilter init(String url,
    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter,
    AuthenticationManager authenticationManager)
    {
        usernamePasswordAuthenticationFilter.setFilterProcessesUrl(url);
        usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return usernamePasswordAuthenticationFilter;
    }
}
