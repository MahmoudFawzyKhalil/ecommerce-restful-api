package gov.iti.jets.rest.resources.order;

import gov.iti.jets.domain.models.Order;
import gov.iti.jets.domain.services.OrderService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path( "orders" )
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class OrderResource {
    @PATCH
    @Path( "{id}" )
    public Response updateOrderStatus( @PathParam( "id" ) int orderId, OrderRequest orderRequest ) {
        Order order = OrderService.updateOrderStatus( orderId, orderRequest.getOrderStatus() );
        OrderResponse orderResponse = new OrderResponse( order );
        orderResponse.setLinks( null );
        return Response.ok().entity( orderResponse ).build();
    }
}
