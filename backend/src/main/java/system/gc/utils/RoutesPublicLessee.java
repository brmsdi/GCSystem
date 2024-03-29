package system.gc.utils;

import lombok.Getter;
import org.springframework.http.HttpMethod;

import static system.gc.utils.TextUtils.API_V1;

@Getter
public enum RoutesPublicLessee {
    LOGIN_LESSEES(new Route(HttpMethod.POST, API_V1.concat("/login/lessees")));

    private final Route route;
    RoutesPublicLessee(Route route)
    {
        this.route = route;
    }

}
