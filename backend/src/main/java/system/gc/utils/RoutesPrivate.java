package system.gc.utils;

import lombok.Getter;

import static system.gc.utils.TextUtils.*;

@Getter
public enum RoutesPrivate {
    // WEB ENDPOINTS
    URL_EMPLOYEE(new RoutePrivate(API_V1_WEB.concat("/employees/**"), new String[]{ROLE_ADMINISTRATOR})),
    URL_LESSEE_SEARCH(new RoutePrivate(API_V1_WEB.concat("/lessees/search"), new String[]{ROLE_ADMINISTRATOR, ROLE_ADMINISTRATIVE_ASSISTANT, ROLE_COUNTER})),
    URL_LESSEE(new RoutePrivate(API_V1_WEB.concat("/lessees/**"), new String[]{ROLE_ADMINISTRATOR, ROLE_ADMINISTRATIVE_ASSISTANT})),
    URL_DEBT(new RoutePrivate(API_V1_WEB.concat("/debts/**"), new String[]{ROLE_ADMINISTRATOR, ROLE_COUNTER})),
    URL_CONDOMINIUM(new RoutePrivate(API_V1_WEB.concat("/condominiums/**"), new String[]{ROLE_ADMINISTRATOR, ROLE_ADMINISTRATIVE_ASSISTANT})),
    URL_CONTRACT(new RoutePrivate(API_V1_WEB.concat("/contracts/**"), new String[]{ROLE_ADMINISTRATOR, ROLE_ADMINISTRATIVE_ASSISTANT})),
    URL_REPAIR_REQUEST(new RoutePrivate(API_V1_WEB.concat("/repair-requests/**"), new String[]{ROLE_ADMINISTRATOR, ROLE_ADMINISTRATIVE_ASSISTANT})),
    URL_ORDER_SERVICE(new RoutePrivate(API_V1_WEB.concat("/order-services/**"), new String[]{ROLE_ADMINISTRATOR, ROLE_ADMINISTRATIVE_ASSISTANT})),
    URL_VALIDATE_TOKEN(new RoutePrivate(API_V1_WEB.concat("/validate/token"), new String[]{ROLE_ADMINISTRATOR, ROLE_ADMINISTRATIVE_ASSISTANT, ROLE_COUNTER})),

    // MOBILE ENDPOINTS
    URL_MOBILE_EMPLOYEE(new RoutePrivate(API_V1_MOBILE.concat("/employees/**"), new String[]{ROLE_ADMINISTRATOR, ROLE_ADMINISTRATIVE_ASSISTANT, ROLE_COUNTER})),
    URL_MOBILE_LESSEE(new RoutePrivate(API_V1_MOBILE.concat("/lessees/**"), new String[]{ROLE_LESSEE})),
    URL_MOBILE_ORDERS(new RoutePrivate(API_V1_MOBILE.concat("/order-services/**"), new String[]{ROLE_ADMINISTRATOR, ROLE_ELECTRICIAN, ROLE_PLUMBER, ROLE_GENERAL_SERVICES}));

    private final RoutePrivate route;

    RoutesPrivate(RoutePrivate route) {
        this.route = route;
    }
}
