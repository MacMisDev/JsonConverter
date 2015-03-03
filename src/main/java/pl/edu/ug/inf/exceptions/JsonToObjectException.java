package pl.edu.ug.inf.exceptions;

public class JsonToObjectException extends Exception{
    public JsonToObjectException() {
    }

    public JsonToObjectException(String message) {
        super(message);
    }

    public JsonToObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
