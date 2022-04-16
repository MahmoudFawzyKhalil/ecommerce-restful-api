package gov.iti.jets.api.utils;

import gov.iti.jets.api.beans.PaginationData;
import gov.iti.jets.api.exceptions.ApiException;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ApiUtils {

    public static URI getCreatedAtUriForPostRequest( UriInfo uriInfo, int id ) {
        return uriInfo.getAbsolutePathBuilder().path( "{id}" ).resolveTemplate( "id", id ).build();
    }


    public static List<String> getFieldsToNullifyForPartialResponse( List<String> allFields, List<String> fieldsWanted ) {
        List<String> fieldsToNullify = new ArrayList<>( allFields );
        fieldsToNullify.removeAll( fieldsWanted );

        if ( fieldsToNullify.size() == allFields.size() ) {
            throw new ApiException( String.format( "None of the fields you have selected for " +
                    "partial response: %s are available on this resource.", fieldsWanted ), 400 );
        }

        return fieldsToNullify;
    }

    public static Link createNextPageLink( UriInfo uriInfo, PaginationData paginationData, int nextOffset ) {
        return Link.fromUriBuilder( uriInfo.getAbsolutePathBuilder()
                        .queryParam( "limit", paginationData.getLimit() )
                        .queryParam( "offset", nextOffset ) )
                .rel( "next" )
                .build();
    }

    public static Link createPreviousPageLink( UriInfo uriInfo, PaginationData paginationData, int previousOffset ) {
        return Link.fromUriBuilder( uriInfo.getAbsolutePathBuilder()
                        .queryParam( "limit", paginationData.getLimit() )
                        .queryParam( "offset", previousOffset ) )
                .rel( "previous" )
                .build();
    }
}
