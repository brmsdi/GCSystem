package system.gc.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsConvert extends UserDetails {
    UserAuthenticatedView getUser();
}
