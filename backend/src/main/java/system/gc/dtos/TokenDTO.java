package system.gc.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Data
@Builder
public class TokenDTO {
    String type;
    String token;
}
