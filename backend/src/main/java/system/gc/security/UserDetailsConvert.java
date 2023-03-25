package system.gc.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface UserDetailsConvert extends UserDetails {
    UserAuthenticatedView getNameAndRoleUser();
}
