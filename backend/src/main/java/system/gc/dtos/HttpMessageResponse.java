package system.gc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Data
@AllArgsConstructor
public class HttpMessageResponse {
    private String key;
    private String message;
}
