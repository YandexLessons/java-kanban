package exceptions;

public class TaskLoadException extends RuntimeException {
    public TaskLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}

