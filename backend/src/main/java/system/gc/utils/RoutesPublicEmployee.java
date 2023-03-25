package system.gc.utils;

import org.springframework.http.HttpMethod;

public enum RoutesPublicEmployee {

    LOGIN_EMPLOYEES(new Route(HttpMethod.POST, "/login/employees"));

    private final Route route;
    RoutesPublicEmployee(Route route)
    {
        this.route = route;
    }

    public Route getRoute()
    {
        return this.route;
    }
}
