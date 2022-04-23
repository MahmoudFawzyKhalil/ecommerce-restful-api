package gov.iti.jets.domain.exceptions;

public class BusinessException extends RuntimeException {
    private int errorCode;

    public BusinessException( String message, Throwable cause, int errorCode ) {
        super( message, cause );
        this.errorCode = errorCode;
    }

    public BusinessException( String message, int errorCode ) {
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
