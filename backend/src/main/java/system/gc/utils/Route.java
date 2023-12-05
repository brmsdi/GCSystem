package system.gc.utils;

import org.springframework.http.HttpMethod;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public record Route(HttpMethod httpMethod, String url) {
}
