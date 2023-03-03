package system.gc.exceptionsAdvice.exceptions;

import java.util.Map;

public abstract class BaseRuntimeException extends RuntimeException implements MessagesException {
    private final Map<String, Object> mapDetails;

    public BaseRuntimeException() {
        this.mapDetails = null;
    }

    public BaseRuntimeException(Map<String, Object> mapDetails) {
        this.mapDetails = mapDetails;
    }

    @Override
    public abstract String getExceptionKey();

    @Override
    public Map<String, Object> getMapDetails() {
        return this.mapDetails;
    }
}
