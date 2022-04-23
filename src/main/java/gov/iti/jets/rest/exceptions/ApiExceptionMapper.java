package gov.iti.jets.rest.exceptions;

import gov.iti.jets.rest.beans.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    @Override
    public Response toResponse( ApiException e ) {
        e.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage( e.getMessage(), e.getErrorCode() );
        return Response.status( e.getErrorCode() ).entity( errorMessage ).build();
    }
}
