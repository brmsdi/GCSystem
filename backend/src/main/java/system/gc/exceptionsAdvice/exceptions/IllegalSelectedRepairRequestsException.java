package system.gc.exceptionsAdvice.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

public class IllegalSelectedRepairRequestsException extends RuntimeException implements BuildErrorException {
    private HttpStatus httpStatus;
    private MessageSource messageSource;
    public IllegalSelectedRepairRequestsException(String message) {
        super(message);
        setHttpStatusType(HttpStatus.BAD_REQUEST);
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
