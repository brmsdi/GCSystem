package system.gc.utils;

import org.springframework.http.HttpMethod;

public enum RoutesPublicLessee {
    LOGIN_LESSEES(new Route(HttpMethod.POST, "/login/lessees"));

    private final Route route;
    RoutesPublicLessee(Route route)
    {
        this.route = route;
    }

    public Route getRoute()
    {
        return this.route;
    }
}
