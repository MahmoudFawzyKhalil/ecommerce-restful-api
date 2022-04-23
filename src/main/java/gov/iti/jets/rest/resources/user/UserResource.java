package gov.iti.jets.rest.resources.user;

import gov.iti.jets.domain.models.User;
import gov.iti.jets.domain.services.UserService;
import gov.iti.jets.rest.beans.PaginationData;
import gov.iti.jets.rest.exceptions.ApiException;
import gov.iti.jets.rest.utils.ApiUtils;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path( "users" )
@Consumes( {"application/json; qs=1", "application/xml; qs=0.75"} )
@Produces( {"application/json; qs=1", "application/xml; qs=0.75"} )
public class UserResource {
    @Context
    private UriInfo uriInfo;


    @GET
    public Response getAllUsers( @BeanParam PaginationData paginationData,
                                 @QueryParam( "fields" ) List<String> fieldsWanted ) {

        List<User> users = UserService.findUsers( paginationData );

        List<UserResponse> userResponses = users.stream()
                .map( UserResponse::new )
                .collect( toList() );
        userResponses.forEach( ur -> addLinksToUserResponse( ur, uriInfo ) );

        ApiUtils.nullifyListFieldsForPartialResponse( userResponses, fieldsWanted, UserResponse.class );

        List<Link> links = ApiUtils.createPaginatedResourceLinks( uriInfo, paginationData, UserService.getNumberOfUsers() );

        UserResponseWrapper userResponseWrapper = new UserResponseWrapper( userResponses, links );

        return Response.ok().entity( userResponseWrapper ).build();
    }


    public static void addLinksToUserResponse( UserResponse userResponse, UriInfo uriInfo ) {
        userResponse.addLink( ApiUtils.createSelfLink( uriInfo, userResponse.getId(), UserResource.class ) );
    }


    @POST
    public Response createUser( UserRequest userRequest ) {
        if ( userRequest == null )
            throw new ApiException( "You must provide a valid POST request body.", 400 );

        User user = new User( userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getRole() );

        UserService.createUser( user );
        UserResponse userResponse = new UserResponse( user );
        addLinksToUserResponse( userResponse, uriInfo );
        URI createdAtUri = ApiUtils.getCreatedAtUriForPostRequest( uriInfo, userResponse.getId() );
        return Response.created( createdAtUri ).entity( userResponse ).build();
    }

    @GET
    @Path( "{id}" )
    public Response findUserById( @PathParam( "id" ) int id ) {
        User user = UserService.findUserById( id ).orElseThrow( () -> new ApiException(
                String.format( "No user exists with the id (%s)", id ), 400 ) );
        UserResponse userResponse = new UserResponse( user );
        addLinksToUserResponse( userResponse, uriInfo );
        return Response.ok().entity( userResponse ).build();
    }

    /*
    @DELETE
    @Path( "{id}" )
    public Response deleteProduct( @PathParam( "id" ) int id ) {
        ProductService.deleteProduct( id );
        return Response.noContent().build();
    }

    @PUT
    @Path( "{id}" )
    public Response updateProduct( @PathParam( "id" ) int id, ProductRequest productRequest ) {
        ProductValidator.validate( productRequest );
        Product product = new Product( productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getQuantity(),
                productRequest.getPrice() );
        product.setId( id );
        ProductService.updateProduct( product );
        ProductResponse productResponse = new ProductResponse( product );
        addLinksToProductResponse( productResponse, uriInfo );
        return Response.ok().entity( productResponse ).build();
    }

    @POST
    @Path( "{pid}/categories/{cid}" )
    public Response addCategoryToProduct( @PathParam( "pid" ) int productId,
                                          @PathParam( "cid" ) int categoryId ) {
        Product product = ProductService.addCategoryToProduct( productId, categoryId );
        ProductResponse productResponse = new ProductResponse( product );
        addLinksToProductResponse( productResponse, uriInfo );
        return Response.ok( productResponse ).build();
    }


    @DELETE
    @Path( "{pid}/categories/{cid}" )
    public Response deleteCategoryFromProduct( @PathParam( "pid" ) int productId,
                                               @PathParam( "cid" ) int categoryId ) {
        Product product = ProductService.deleteCategoryFromProduct( productId, categoryId );
        ProductResponse productResponse = new ProductResponse( product );
        addLinksToProductResponse( productResponse, uriInfo );
        return Response.ok( productResponse ).build();
    }
    */

}
