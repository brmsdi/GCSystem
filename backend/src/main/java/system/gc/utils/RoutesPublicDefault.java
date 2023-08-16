package system.gc.utils;

import lombok.Getter;
import org.springframework.http.HttpMethod;

import static system.gc.utils.TextUtils.API_V1_WEB;

@Getter
public enum RoutesPublicDefault {
    EMPLOYEES_PASSWORD_REQUEST_CODE(new Route(HttpMethod.POST, API_V1_WEB.concat("/employees/password/request-code"))),
    LESSEES_PASSWORD_REQUEST_CODE(new Route(HttpMethod.POST, API_V1_WEB.concat("/lessees/password/request-code"))),
    EMPLOYEES_PASSWORD_RECEIVE_CODE(new Route(HttpMethod.POST, API_V1_WEB.concat("/employees/password/receive-code"))),
    LESSEES_PASSWORD_RECEIVE_CODE(new Route(HttpMethod.POST, API_V1_WEB.concat("/lessees/password/receive-code"))),
    EMPLOYEES_PASSWORD_CHANGE(new Route(HttpMethod.PUT, API_V1_WEB.concat("/employees/password/change"))),
    LESSEES_PASSWORD_CHANGE(new Route(HttpMethod.PUT, API_V1_WEB.concat("/lessees/password/change")));

    private final Route route;
    RoutesPublicDefault(Route route)
    {
        this.route = route;
    }

}
