package system.gc.utils;

import lombok.Getter;
import org.springframework.http.HttpMethod;

import static system.gc.utils.TextUtils.*;

@Getter
public enum RoutesPublicEmployee {

    LOGIN_EMPLOYEES(new Route(HttpMethod.POST, API_V1.concat("/login/employees")));

    private final Route route;
    RoutesPublicEmployee(Route route)
    {
        this.route = route;
    }

}
