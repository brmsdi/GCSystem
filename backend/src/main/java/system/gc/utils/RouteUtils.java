package system.gc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        Stream.of(RoutesPublicDefault.values()).forEach(routesPublicDefault -> allowed.add(routesPublicDefault.getRoute()));
        Stream.of(RoutesPublicEmployee.values()).forEach(routesPublicEmployee -> allowed.add(routesPublicEmployee.getRoute()));
        Stream.of(RoutesPublicLessee.values()).forEach(routesPublicLessee -> allowed.add(routesPublicLessee.getRoute()));
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
