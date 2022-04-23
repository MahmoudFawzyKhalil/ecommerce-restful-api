package gov.iti.jets.soap.exceptions;

public class SOAPApiException extends RuntimeException {
    public SOAPApiException( String message ) {
        super( message );
    }

    public SOAPApiException( String message, Throwable cause ) {
        super( message, cause );
    }
}
