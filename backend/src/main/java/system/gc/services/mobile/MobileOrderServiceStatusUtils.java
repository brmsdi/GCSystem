package system.gc.services.mobile;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import system.gc.entities.Status;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileOrderServiceStatusUtils {
    default boolean statusEquals(String name, Status status) {
        return status.getName().equalsIgnoreCase(name);
    }

    default void statusIsEditable(String name, Status status, MessageSource messageSource) throws IllegalChangeOrderServiceException {
        if (statusEquals(name, status))
            throw new IllegalChangeOrderServiceException(messageSource.getMessage("TEXT_ERROR_ORDER_SERVICE_STATUS_CONCLUDED", null, LocaleContextHolder.getLocale()));
    }
}
