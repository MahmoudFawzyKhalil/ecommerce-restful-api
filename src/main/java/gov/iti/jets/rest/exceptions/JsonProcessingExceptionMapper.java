package gov.iti.jets.rest.exceptions;

import gov.iti.jets.rest.beans.ErrorMessage;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {
    @Override
    public Response toResponse( ProcessingException e ) {
        e.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage( "Please provide a valid JSON request body.", 400 );
        return Response.status( 400 ).entity( errorMessage ).build();
    }
}
