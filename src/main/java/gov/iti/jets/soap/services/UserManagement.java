package gov.iti.jets.soap.services;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;


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

}
