package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Getter
@Setter
public class ApiErrorDTO {
    private Date timestamp;
    private Integer status;
    private String code;
    private Set<ErrorDTO> errors;

    public ApiErrorDTO() {
    }

    public ApiErrorDTO(Date timestamp, Integer status, String code, Set<ErrorDTO> errors) {
        setTimestamp(timestamp);
        setStatus(status);
        setCode(code);
        setErrors(errors);
    }
}
