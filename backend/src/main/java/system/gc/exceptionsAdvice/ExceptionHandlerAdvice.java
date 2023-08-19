package system.gc.exceptionsAdvice;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import system.gc.dtos.ApiErrorDTO;
import system.gc.dtos.ErrorDTO;
import system.gc.exceptionsAdvice.exceptions.*;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Log4j2
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final MessageSource messageSource;

    public ExceptionHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handlerMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        log.error("Exception {}, Message: {}",
                methodArgumentNotValidException.getClass().getName(),
                methodArgumentNotValidException.getMessage());

        Set<ErrorDTO> errorsDTO = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> buildError(error, "required.validation"))
                .collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errorsDTO));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> entityNotFoundException(EntityNotFoundException exception) {

        log.error(exception.getMessage());
        ErrorDTO errorDTO = buildError(HttpStatus.NOT_FOUND.toString(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(baseErrorBuilder(HttpStatus.NOT_FOUND, Set.of(errorDTO)));
    }

    @ExceptionHandler(CodeChangePasswordInvalidException.class)
    public ResponseEntity<ApiErrorDTO> codeChangePasswordInvalidException(CodeChangePasswordInvalidException exception) {

        log.error(exception.getMessage());
        ErrorDTO errorDTO = buildError(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseErrorBuilder(HttpStatus.BAD_REQUEST, Set.of(errorDTO)));
    }

    @ExceptionHandler(CodeChangeOpenedException.class)
    public ResponseEntity<ApiErrorDTO> codeChangeOpenedException(CodeChangeOpenedException exception) {

        log.error(exception.getMessage());
        ErrorDTO errorDTO = buildError(HttpStatus.NOT_ACCEPTABLE.toString(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(baseErrorBuilder(HttpStatus.NOT_ACCEPTABLE, Set.of(errorDTO)));
    }

    @ExceptionHandler(DuplicatedFieldException.class)
    public ResponseEntity<ApiErrorDTO> duplicatedFieldException(DuplicatedFieldException exception) {
        log.error(exception.getMessage());
        ErrorDTO errorDTO = buildError(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseErrorBuilder(HttpStatus.BAD_REQUEST, Set.of(errorDTO)));
    }

    @ExceptionHandler({DebtNotCreatedException.class})
    public ResponseEntity<ApiErrorDTO> debtNotCreatedException(DebtNotCreatedException exception)
    {
        log.error(exception.getMessage());
        ErrorDTO errorDTO = buildError(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseErrorBuilder(HttpStatus.BAD_REQUEST, Set.of(errorDTO)));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorDTO> missingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.error(exception.getMessage());
        ErrorDTO errorDTO = buildError(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseErrorBuilder(HttpStatus.BAD_REQUEST, Set.of(errorDTO)));
    }

    @ExceptionHandler(IllegalSelectedRepairRequestsException.class)
    public ResponseEntity<ApiErrorDTO> illegalSelectedRepairRequestsException(IllegalSelectedRepairRequestsException exception) {
        log.error(exception.getMessage());
        ErrorDTO errorDTO = buildError(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseErrorBuilder(HttpStatus.BAD_REQUEST, Set.of(errorDTO)));
    }

    @ExceptionHandler(AccessDeniedOrderService.class)
    public ResponseEntity<ApiErrorDTO> AccessDeniedOrderService(AccessDeniedOrderService exception) {
        log.error(exception.getMessage());
        ErrorDTO errorDTO = buildError(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseErrorBuilder(HttpStatus.BAD_REQUEST, Set.of(errorDTO)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorDTO> constraintViolationException(ConstraintViolationException exception) {
        log.error(exception.getLocalizedMessage());
        ErrorDTO errorDTO = buildError(HttpStatus.BAD_REQUEST.toString(), "Há campos não preenchidos");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseErrorBuilder(HttpStatus.BAD_REQUEST, Set.of(errorDTO)));
    }

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ApiErrorDTO> handlerBaseException(Throwable exception) {
        log.error("Exception {}", exception.getClass().getName());
        MessagesException messageException = (MessagesException) exception;
        ErrorDTO error = buildError(messageException.getExceptionKey(),
                bindExceptionKeywords(messageException.getMapDetails(), messageException.getExceptionKey()));
        Set<ErrorDTO> errors = Set.of(error);
        ApiErrorDTO apiErrorDto = baseErrorBuilder(getResponseStatus(exception), errors);
        return ResponseEntity
                .status(getResponseStatus(exception))
                .body(apiErrorDto);
    }

    private ErrorDTO buildError(String code, String message) {
        return new ErrorDTO(code, message);
    }

    private ErrorDTO buildError(FieldError error, String messageKey) {
        String message = messageSource.getMessage(messageKey, new Object[]{error.getField()}, LocaleContextHolder.getLocale());
        return new ErrorDTO(error.getCode(), message);
    }

    private ApiErrorDTO baseErrorBuilder(HttpStatus httpStatus, Set<ErrorDTO> errorList) {
        return new ApiErrorDTO(
                new Date(),
                httpStatus.value(),
                httpStatus.name(),
                errorList);
    }

    private String bindExceptionKeywords(Map<String, Object> keywords, String exceptionKey) {
        String message = messageSource.getMessage(exceptionKey, null, LocaleContextHolder.getLocale());

        return Objects.nonNull(keywords) ? new StrSubstitutor(keywords).replace(message) : message;
    }

    private HttpStatus getResponseStatus(Throwable exception) {
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        if (exception.getClass().getAnnotation(ResponseStatus.class) == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return responseStatus.value();
    }

}
