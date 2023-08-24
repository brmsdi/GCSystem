package system.gc.controllers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface Pagination {

    default void pageLimit(Integer page) throws IllegalArgumentException {
        if (page < 0) throw new IllegalArgumentException(messageSource().getMessage("TEXT_ERROR_PAGINATION_PAGE", new Object[]{page}, LocaleContextHolder.getLocale()));
    }

    default void sizeLimit(Integer size) throws IllegalArgumentException {
        if (size <= 0)
            throw new IllegalArgumentException(messageSource().getMessage("TEXT_ERROR_PAGINATION_SIZE", new Object[]{size}, LocaleContextHolder.getLocale()));
        else if(size > 10)
            throw new IllegalArgumentException(messageSource().getMessage("TEXT_ERROR_PAGINATION_SIZE_BIGGER_THAN", new Object[]{10}, LocaleContextHolder.getLocale()));
    }

    MessageSource messageSource();
}
