package system.gc.configuration.exceptions;

import java.util.Map;

public interface MessagesException {
    String getExceptionKey();
    Map<String, Object> getMapDetails();
}
