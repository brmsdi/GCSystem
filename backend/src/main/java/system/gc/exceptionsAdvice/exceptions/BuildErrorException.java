package system.gc.exceptionsAdvice.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import system.gc.dtos.ApiErrorDTO;
import system.gc.dtos.ErrorDTO;

import java.util.Date;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface BuildErrorException {

    default ApiErrorDTO build() {
        HttpStatus httpStatus = getHttpStatusType();
        ErrorDTO errorDTO = buildError(httpStatus.toString(), getMessageError());
        return baseErrorBuilder(httpStatus, Set.of(errorDTO));
    }
    default ErrorDTO buildError(String code, String message) {
        return new ErrorDTO(code, message);
    }

    default ErrorDTO buildError(FieldError error, String messageKey) {
        String message = messageSource().getMessage(messageKey, new Object[]{error.getField()}, LocaleContextHolder.getLocale());
        return new ErrorDTO(error.getCode(), message);
    }

    default ApiErrorDTO baseErrorBuilder(HttpStatus httpStatus, Set<ErrorDTO> errorList) {
        return new ApiErrorDTO(
                new Date(),
                httpStatus.value(),
                httpStatus.name(),
                errorList);
    }

    String getMessageError();

    MessageSource messageSource();

    void setHttpStatusType(HttpStatus httpStatus);
    HttpStatus getHttpStatusType();
}
