package system.gc.exceptionsAdvice.exceptions;

import java.util.Map;

public interface MessagesException {
    String getExceptionKey();

    Map<String, Object> getMapDetails();
}
