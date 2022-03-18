package system.gc.security;

import lombok.Getter;
import lombok.Setter;

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
