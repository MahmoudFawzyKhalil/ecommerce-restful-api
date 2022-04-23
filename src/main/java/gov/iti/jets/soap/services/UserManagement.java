package gov.iti.jets.soap.services;

import gov.iti.jets.domain.models.User;
import gov.iti.jets.domain.services.UserService;
import gov.iti.jets.rest.exceptions.ApiException;
import gov.iti.jets.rest.resources.cart.CartResponse;
import gov.iti.jets.soap.exceptions.SOAPApiException;
import gov.iti.jets.soap.services.dtos.CartDto;
import gov.iti.jets.soap.services.dtos.CartLineItemRequestDto;
import gov.iti.jets.soap.services.dtos.UserDto;
import gov.iti.jets.soap.services.dtos.UserRequestDto;
import jakarta.jws.*;
import jakarta.ws.rs.core.Response;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings( "NonJaxWsWebServices" )
@WebService( targetNamespace = "http://www.jets.gov.iti.eg/ecommerce",
        name = "UserManagement",
        portName = "UserManagementPort",
        serviceName = "UserManagementService" )
@BindingType( SOAPBinding.SOAP12HTTP_BINDING )
public class UserManagement {

    @WebMethod
    @WebResult( name = "echoedString", partName = "echoedStringPartName" )
    public String echo( @WebParam( name = "stringToEcho", partName = "stringToEchoPartName" ) String s ) {
        return s;
    }

    @WebMethod
    @WebResult( name = "users" )
    public List<UserDto> getAllUsers() {
        return UserService.getAllUsers()
                .stream()
                .map( UserDto::new )
                .collect( Collectors.toList() );
    }

    @WebMethod
    @WebResult( name = "user" )
    public UserDto createOrUpdateUser( @WebParam( name = "user" ) UserRequestDto userRequestDto ) {
        System.out.println( userRequestDto );
        User user = new User( userRequestDto.getFirstName(),
                userRequestDto.getLastName(),
                userRequestDto.getEmail(),
                userRequestDto.getRole() );
        if ( userRequestDto.getId() != null ) {
            user.setId( userRequestDto.getId() );
        }
        UserService.updateUser( user );
        return new UserDto( user );
    }

    @WebMethod
    @WebResult( name = "user" )
    public UserDto findUserById( @WebParam( name = "userId" ) int userId ) {
        return UserService.findUserById( userId ).map( UserDto::new ).orElseThrow( () -> new SOAPApiException( "No user exists with the id " + userId ) );
    }

    @WebMethod
    @Oneway
    public void deleteUser( @WebParam( name = "userId" ) int userId ) {
        UserService.deleteUser( userId );
    }

    @WebMethod
    @WebResult( name = "cart" )
    public CartDto getCartByUserId( @WebParam( name = "userId" ) int userId ) {
        return UserService.findCartByUserId( userId ).map( CartDto::new ).orElseThrow( () ->
                new SOAPApiException( String.format( "No user exists with the id (%s)", userId ) ) );
    }

    @WebMethod
    @WebResult( name = "cart" )
    public CartDto addItemToUserCart( @WebParam( name = "userId" ) int userId, @WebParam( name = "cartLineItem" ) CartLineItemRequestDto dto ) {
        var cart = UserService.addItemToUserCart( userId, dto.getProductId(), dto.getQuantity() );
        return new CartDto( cart );
    }

    @WebMethod
    @Oneway
    public void clearUserCart( @WebParam( name = "userId" ) int userId ) {
        UserService.clearUserCart( userId );
    }

/*    @WebMethod
    @WebResult( name = "cart" )
    public CartDto addItemToUserCart( @WebParam( name = "userId" ) int userId, @WebParam( name = "cartLineItem" ) CartLineItemRequestDto dto ) {
        var cart = UserService.addItemToUserCart( userId, dto.getProductId(), dto.getQuantity() );
        return new CartDto( cart );
    }*/

}
