package gov.iti.jets.rest.exceptions;

public class ApiException extends RuntimeException {
    private int errorCode;

    public ApiException( String message, Throwable cause, int errorCode ) {
        super( message, cause );
        this.errorCode = errorCode;
    }

    public ApiException( String message, int errorCode ) {
        super( message );
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode( int errorCode ) {
        this.errorCode = errorCode;
    }
}
