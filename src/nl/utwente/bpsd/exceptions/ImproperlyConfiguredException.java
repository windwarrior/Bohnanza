package nl.utwente.bpsd.exceptions;

// Thrown when things are not configured as they should be
public class ImproperlyConfiguredException extends RuntimeException {

    public ImproperlyConfiguredException(String msg) {
        super(msg);
    }
    
}
