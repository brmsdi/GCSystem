package system.gc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 1.3
 */
public class RouteUtils {
    /**
     * <p>Esta rotina retona todas as rotas públicas do sistema.</p>
     * @return Lista de Rotas
     * @see system.gc.security.Filter.JWTAuthenticationFilter
     * @see system.gc.configuration.DefaultConfiguration
     * @see system.gc.configuration.EmployeeConfiguration
     * @see system.gc.configuration.LesseeConfiguration
     */
    public static List<Route> getAllPublicRoutes()
    {
        List<Route> allowed = new ArrayList<>();
        allowed.addAll(Arrays.stream(RoutesPublicDefault.values()).map(RoutesPublicDefault::getRoute).toList());
        allowed.addAll(Arrays.stream(RoutesPublicEmployee.values()).map(RoutesPublicEmployee::getRoute).toList());
        allowed.addAll(Arrays.stream(RoutesPublicLessee.values()).map(RoutesPublicLessee::getRoute).toList());
        return allowed;
    }

    /**
     * <p>Esta rotina retorna todas as URIs das rotas públicas do sistema.</p>
     * @return Lista de URIs
     */
    public static List<String> getAllPublicRoutesURI()
    {
        return getAllPublicRoutes().stream().map(Route::url).toList();
    }
}
