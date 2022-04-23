package gov.iti.jets.rest.resources.product;

import gov.iti.jets.rest.exceptions.ApiException;
import gov.iti.jets.rest.utils.ValidationUtil;

import java.util.Objects;

public class ProductValidator {
    public static void validate( ProductRequest productRequest ) {
        if ( Objects.isNull( productRequest ) )
            throw new ApiException( "You must provide a request body.", 400 );

        ValidationUtil.validate( productRequest );
    }
}
