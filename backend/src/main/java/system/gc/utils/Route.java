package system.gc.utils;

import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
@Data
public class Route {
    private HttpMethod httpMethod;
    private String route;
    public Route(HttpMethod httpMethod, String route){
        this.httpMethod = httpMethod;
        this.route = route;
    }
}
