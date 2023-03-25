package system.gc.utils;

import org.springframework.http.HttpMethod;

public enum RoutesPublicDefault {
    EMPLOYEES_PASSWORD_REQUEST_CODE(new Route(HttpMethod.POST, "/employees/password/request-code")),
    LESSEES_PASSWORD_REQUEST_CODE(new Route(HttpMethod.POST, "/lessees/password/request-code")),
    EMPLOYEES_PASSWORD_RECEIVE_CODE(new Route(HttpMethod.POST, "/employees/password/receive-code")),
    LESSEES_PASSWORD_RECEIVE_CODE(new Route(HttpMethod.POST, "/lessees/password/receive-code")),
    EMPLOYEES_PASSWORD_CHANGE(new Route(HttpMethod.PUT, "/employees/password/change")),
    LESSEES_PASSWORD_CHANGE(new Route(HttpMethod.PUT, "/lessees/password/change"));

    private final Route route;
    RoutesPublicDefault(Route route)
    {
        this.route = route;
    }

    public Route getRoute()
    {
        return this.route;
    }
}
