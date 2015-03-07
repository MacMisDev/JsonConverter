package pl.edu.ug.inf.exceptions;

public class JsonConvertToObjectException extends Exception{
    public JsonConvertToObjectException() {
    }

    public JsonConvertToObjectException(String message) {
        super(message);
    }

    public JsonConvertToObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
