package cloudcomputing.accessmonitor.portal;

import javax.servlet.ServletException;

public class NoFaceException extends ServletException {

    public NoFaceException() {
    }

    public NoFaceException(String message) {
        super(message);
    }

    public NoFaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFaceException(Throwable cause) {
        super(cause);
    }
}
