package gov.iti.jets.api.exceptions;

import gov.iti.jets.api.beans.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ThrowableExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse( Throwable e ) {
        e.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage( e.getMessage(), 400 );
        return Response.status( 400 ).entity( errorMessage ).build();
    }
}
