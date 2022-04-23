package gov.iti.jets.soap.services;

import gov.iti.jets.domain.services.UserService;
import gov.iti.jets.soap.services.dtos.UserDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

import java.util.List;
import java.util.stream.Collectors;


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


}
