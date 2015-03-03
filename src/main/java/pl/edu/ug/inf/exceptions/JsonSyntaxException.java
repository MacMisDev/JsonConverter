package pl.edu.ug.inf.exceptions;

public class JsonSyntaxException extends Exception{
    public JsonSyntaxException() {
    }

    public JsonSyntaxException(String message) {
        super(message);
    }

    public JsonSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
}
