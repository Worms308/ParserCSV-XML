package exceptions;

public class ExtensionNotSupportedException extends RuntimeException{
    public ExtensionNotSupportedException() {
        super();
    }

    public ExtensionNotSupportedException(String message) {
        super(message);
    }
}
