package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    private String key;
    private String message;

    public ErrorDTO() {
    }

    public ErrorDTO(String key, String message) {
        this.setKey(key);
        this.setMessage(message);
    }
}
