package system.gc.utils;

import lombok.Getter;
import org.springframework.http.HttpMethod;

import static system.gc.utils.TextUtils.API_V1_MOBILE;
import static system.gc.utils.TextUtils.API_V1_WEB;

@Getter
public enum RoutesPublicDefault {
    //WEB
    WEB_EMPLOYEES_PASSWORD_REQUEST_CODE(new Route(HttpMethod.POST, API_V1_WEB.concat("/employees/password/request-code"))),
    WEB_LESSEES_PASSWORD_REQUEST_CODE(new Route(HttpMethod.POST, API_V1_WEB.concat("/lessees/password/request-code"))),
    WEB_EMPLOYEES_PASSWORD_RECEIVE_CODE(new Route(HttpMethod.POST, API_V1_WEB.concat("/employees/password/receive-code"))),
    WEB_LESSEES_PASSWORD_RECEIVE_CODE(new Route(HttpMethod.POST, API_V1_WEB.concat("/lessees/password/receive-code"))),
    WEB_EMPLOYEES_PASSWORD_CHANGE(new Route(HttpMethod.PUT, API_V1_WEB.concat("/employees/password/change"))),
    WEB_LESSEES_PASSWORD_CHANGE(new Route(HttpMethod.PUT, API_V1_WEB.concat("/lessees/password/change"))),
    // MOBILE
    MOBILE_EMPLOYEES_PASSWORD_REQUEST_CODE(new Route(HttpMethod.POST, API_V1_MOBILE.concat("/employees/password/request-code"))),
    MOBILE_LESSEES_PASSWORD_REQUEST_CODE(new Route(HttpMethod.POST, API_V1_MOBILE.concat("/lessees/password/request-code"))),
    MOBILE_EMPLOYEES_PASSWORD_RECEIVE_CODE(new Route(HttpMethod.POST, API_V1_MOBILE.concat("/employees/password/receive-code"))),
    MOBILE_LESSEES_PASSWORD_RECEIVE_CODE(new Route(HttpMethod.POST, API_V1_MOBILE.concat("/lessees/password/receive-code"))),
    MOBILE_EMPLOYEES_PASSWORD_CHANGE(new Route(HttpMethod.PUT, API_V1_MOBILE.concat("/employees/password/change"))),
    MOBILE_LESSEES_PASSWORD_CHANGE(new Route(HttpMethod.PUT, API_V1_MOBILE.concat("/lessees/password/change")));
    private final Route route;
    RoutesPublicDefault(Route route)
    {
        this.route = route;
    }

}
