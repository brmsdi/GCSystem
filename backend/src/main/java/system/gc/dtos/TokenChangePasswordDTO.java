package system.gc.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenChangePasswordDTO {
    String type;
    String token;
    String newPassword;
}

