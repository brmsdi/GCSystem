package system.gc.dtos;

public class ErrorDTO {
    private String key;
    private String message;

    public ErrorDTO() {
    }

    public ErrorDTO(String key, String message) {
        this.setKey(key);
        this.setMessage(message);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
