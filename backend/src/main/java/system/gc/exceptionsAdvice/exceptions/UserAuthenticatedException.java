package system.gc.exceptionsAdvice.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public class UserAuthenticatedException extends Exception implements BuildErrorException {
    private HttpStatus httpStatus;
    private MessageSource messageSource;
    public UserAuthenticatedException(String message) {
        super(message);
        setHttpStatusType(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public String getMessageError() {
        return getMessage();
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }

    @Override
    public void setHttpStatusType(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getHttpStatusType() {
        return this.httpStatus;
    }
}
