package gov.iti.jets.rest.resources.order;

import gov.iti.jets.domain.models.Cart;
import gov.iti.jets.domain.models.User;
import gov.iti.jets.domain.services.UserService;
import gov.iti.jets.rest.beans.PaginationData;
import gov.iti.jets.rest.exceptions.ApiException;
import gov.iti.jets.rest.resources.cart.CartLineItemRequest;
import gov.iti.jets.rest.resources.cart.CartResponse;
import gov.iti.jets.rest.resources.user.UserRequest;
import gov.iti.jets.rest.resources.user.UserResponse;
import gov.iti.jets.rest.resources.user.UserResponseWrapper;
import gov.iti.jets.rest.utils.ApiUtils;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path( "orders" )
public class OrderResource {

}
