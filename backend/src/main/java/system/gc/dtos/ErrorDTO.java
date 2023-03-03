package system.gc.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDTO {
    private String key;
    private String message;

    public ErrorDTO(){}
    public ErrorDTO(String message){
        this.message = message;
    }

    public ErrorDTO(String key, String message) {
        this.setKey(key);
        this.setMessage(message);
    }
}
