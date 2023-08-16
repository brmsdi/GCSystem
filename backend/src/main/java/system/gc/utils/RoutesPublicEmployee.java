package system.gc.utils;

import lombok.Getter;
import org.springframework.http.HttpMethod;

import static system.gc.utils.TextUtils.API_V1_WEB;

@Getter
public enum RoutesPublicEmployee {

    LOGIN_EMPLOYEES(new Route(HttpMethod.POST, API_V1_WEB.concat("/login/employees")));

    private final Route route;
    RoutesPublicEmployee(Route route)
    {
        this.route = route;
    }

}
