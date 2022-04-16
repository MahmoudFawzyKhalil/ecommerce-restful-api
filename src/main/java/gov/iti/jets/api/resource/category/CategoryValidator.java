package gov.iti.jets.api.resource.category;

import gov.iti.jets.api.exceptions.ApiException;

import java.util.Objects;

public class CategoryValidator {
    public static void ensureCategoryRequestIsValidForCreate( CategoryRequest categoryRequest ) {
        if ( Objects.isNull( categoryRequest ) )
            throw new ApiException( "You must provide a request body.", 400 );

        if ( Objects.isNull( categoryRequest.getName() ) )
            throw new ApiException( "You must provide a name for the category.", 400 );
    }
}
