package gov.iti.jets.rest.resources.user;

import gov.iti.jets.domain.models.Cart;
import gov.iti.jets.domain.models.Order;
import gov.iti.jets.domain.models.User;
import gov.iti.jets.domain.services.UserService;
import gov.iti.jets.rest.beans.PaginationData;
import gov.iti.jets.rest.exceptions.ApiException;
import gov.iti.jets.rest.resources.cart.CartLineItemRequest;
import gov.iti.jets.rest.resources.cart.CartResponse;
import gov.iti.jets.rest.resources.order.OrderResource;
import gov.iti.jets.rest.resources.order.OrderResponse;
import gov.iti.jets.rest.resources.order.OrderResponseWrapper;
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

    @DELETE
    @Path( "{id}" )
    public Response deleteUser( @PathParam( "id" ) int id ) {
        UserService.deleteUser( id );
        return Response.noContent().build();
    }

    @PUT
    @Path( "{id}" )
    public Response updateUser( @PathParam( "id" ) int id, UserRequest userRequest ) {
        if ( userRequest == null )
            throw new ApiException( "You must provide a valid POST request body.", 400 );

        User user = new User( userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getRole() );
        user.setId( id );

        UserService.updateUser( user );
        UserResponse userResponse = new UserResponse( user );
        addLinksToUserResponse( userResponse, uriInfo );
        return Response.ok().entity( userResponse ).build();
    }


    @GET
    @Path( "{id}/cart" )
    public Response getCartByUserId( @PathParam( "id" ) int id ) {
        CartResponse cartResponse = UserService.findCartByUserId( id ).map( CartResponse::new ).orElseThrow( () ->
                new ApiException( String.format( "No user exists with the id (%s)", id ), 400 ) );
        addLinksToCartResponse( cartResponse, uriInfo );
        return Response.ok().entity( cartResponse ).build();
    }

    public static void addLinksToCartResponse( CartResponse cartResponse, UriInfo uriInfo ) {
        cartResponse.addLink( ApiUtils.createAbsoluteSelfLink( uriInfo ) );
    }

    @POST
    @Path( "{id}/cart" )
    public Response addItemToCart( @PathParam( "id" ) int userId, CartLineItemRequest cartLineItemRequest ) {
        Cart cart = UserService.addItemToUserCart( userId, cartLineItemRequest.getProductId(), cartLineItemRequest.getQuantity() );
        CartResponse cartResponse = new CartResponse( cart );
        addLinksToCartResponse( cartResponse, uriInfo );
        return Response.ok().entity( cartResponse ).build();
    }

    @DELETE
    @Path( "{id}/cart" )
    public Response clearShoppingCart( @PathParam( "id" ) int userId ) {
        UserService.clearUserCart( userId );
        return Response.noContent().build();
    }

    @GET
    @Path( "{id}/orders" )
    public Response getOrdersByUserId( @PathParam( "id" ) int userId ) {

        List<OrderResponse> orders = UserService.getOrdersByUserId( userId ).stream()
                .map( OrderResponse::new )
                .collect( toList() );

        OrderResponseWrapper orderResponseWrapper = new OrderResponseWrapper( orders,
                List.of( ApiUtils.createAbsoluteSelfLink( uriInfo ) ) );

        return Response.ok().entity( orderResponseWrapper ).build();
    }

    @POST
    @Path( "{id}/orders" )
    public Response createOrderFromUserCart( @PathParam( "id" ) int userId ) {
        Order order = UserService.createOrderFromUserCart( userId );
        OrderResponse orderResponse = new OrderResponse( order );
        orderResponse.setLinks( null );
        return Response.ok().entity( orderResponse ).build();
    }
}
