package gov.iti.jets.rest.utils;

import gov.iti.jets.rest.beans.PaginationData;
import gov.iti.jets.rest.exceptions.ApiException;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriInfo;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ApiUtils {

    public static URI getCreatedAtUriForPostRequest( UriInfo uriInfo, int id ) {
        return uriInfo.getAbsolutePathBuilder().path( "{id}" ).resolveTemplate( "id", id ).build();
    }

    public static <T> void nullifyListFieldsForPartialResponse( List<T> records, List<String> wantedFieldNames, Class<T> clazz ) {

        if ( wantedFieldNames == null || wantedFieldNames.isEmpty() )
            return;

        try {

            var fieldsToNullifyNames = Arrays.stream( clazz.getDeclaredFields() )
                    .map( Field::getName )
                    .collect( Collectors.toList() );

            boolean anyValidFieldsWereSelected = fieldsToNullifyNames.removeAll( wantedFieldNames );

            if ( !anyValidFieldsWereSelected ) {
                throw new ApiException( String.format( "All of the fields you have provided for partial response %s are invalid.", wantedFieldNames ),
                        400 );
            }

            for ( var record : records ) {
                for ( var fieldName : fieldsToNullifyNames ) {
                    var field = record.getClass().getDeclaredField( fieldName );

                    if ( field.getName().equals( "links" ) )
                        continue;

                    field.setAccessible( true );
                    field.set( record, null );
                }
            }
        } catch ( NoSuchFieldException | IllegalAccessException e ) {
            e.printStackTrace();
            throw new ServerErrorException( 500, e );
        }
    }

    public static List<Link> createPaginatedResourceLinks( UriInfo uriInfo, PaginationData paginationData, long numberOfRecords ) {

        Link self = createAbsoluteSelfLink( uriInfo );
        Optional<Link> nextPage = createNextPageLink( uriInfo, paginationData, numberOfRecords );
        Optional<Link> previousPage = createPreviousPageLink( uriInfo, paginationData );

        List<Link> links = new ArrayList<>();

        links.add( self );
        nextPage.ifPresent( links::add );
        previousPage.ifPresent( links::add );

        return links;
    }

    public static Link createAbsoluteSelfLink( UriInfo uriInfo ) {
        return Link.fromUriBuilder( uriInfo.getAbsolutePathBuilder() ).rel( "self" ).build();
    }

    public static Optional<Link> createNextPageLink( UriInfo uriInfo, PaginationData paginationData, long numberOfRecords ) {
        int nextOffset = paginationData.getOffset() + paginationData.getLimit();

        if ( nextOffset > numberOfRecords ) {
            return Optional.empty();
        }

        return Optional.of( Link.fromUriBuilder( uriInfo.getAbsolutePathBuilder()
                        .queryParam( "limit", paginationData.getLimit() )
                        .queryParam( "offset", nextOffset ) )
                .rel( "next" )
                .build() );
    }

    public static Optional<Link> createPreviousPageLink( UriInfo uriInfo, PaginationData paginationData ) {
        int previousOffset = paginationData.getOffset() - paginationData.getLimit();

        if ( previousOffset < 0 ) {
            return Optional.empty();
        }

        return Optional.of( Link.fromUriBuilder( uriInfo.getAbsolutePathBuilder()
                        .queryParam( "limit", paginationData.getLimit() )
                        .queryParam( "offset", previousOffset ) )
                .rel( "previous" )
                .build() );
    }

    public static Link createSelfLinkForResponseWithoutIdPathParam( UriInfo uriInfo, int id ) {
        return Link.fromUriBuilder( uriInfo.getAbsolutePathBuilder().path( String.valueOf( id ) ) ).rel( "self" ).build();
    }

    public static <T> Link createSelfLinkForResponseNestedCollection( UriInfo uriInfo, Integer id, Class<T> resourceClass ) {
        return Link.fromUriBuilder( uriInfo.getBaseUriBuilder()
                        .path( resourceClass )
                        .path( String.valueOf( id ) ) )
                .rel( "self" )
                .build();
    }

    public static <T> Link createSelfLink( UriInfo uriInfo, int id, Class<T> resourceClass ) {
        return Link.fromUriBuilder( uriInfo.getBaseUriBuilder()
                        .path( resourceClass )
                        .path( String.valueOf( id ) ) )
                .rel( "self" )
                .build();
    }
}
