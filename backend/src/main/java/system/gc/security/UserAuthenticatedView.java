package system.gc.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Getter
@Setter
public class UserAuthenticatedView {
    private String name;
    private String role;
    public UserAuthenticatedView() {}

    public UserAuthenticatedView(String name, String role) {
        setName(name);
        setRole(role);
    }
}
