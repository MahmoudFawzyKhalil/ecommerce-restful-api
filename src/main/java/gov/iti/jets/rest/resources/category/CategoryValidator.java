package gov.iti.jets.rest.resources.category;

import gov.iti.jets.rest.exceptions.ApiException;
import gov.iti.jets.rest.utils.ValidationUtil;

import java.util.Objects;

public class CategoryValidator {
    public static void ensureCategoryRequestIsValid( CategoryRequest categoryRequest ) {
        if ( Objects.isNull( categoryRequest ) )
            throw new ApiException( "You must provide a request body.", 400 );

        ValidationUtil.validate( categoryRequest );
    }
}
