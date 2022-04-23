package gov.iti.jets.rest.resources.user;

import gov.iti.jets.rest.utils.LinkJsonbAdapter;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.List;

@XmlRootElement( name = "users" )
@XmlType( propOrder = {"userResponses", "links"} )
@JsonbPropertyOrder( {"userResponses", "links"} )
public class UserResponseWrapper {

    @JsonbProperty( "users" )
    private List<UserResponse> userResponses;
    @JsonbTypeAdapter( LinkJsonbAdapter.class )
    private List<Link> links;

    public UserResponseWrapper() {
    }

    public UserResponseWrapper( List<UserResponse> userResponses, List<Link> links ) {
        this.userResponses = userResponses;
        this.links = links;
    }

    @XmlElement( name = "user" )
    public List<UserResponse> getUserResponses() {
        return userResponses;
    }

    public void setUserResponses( List<UserResponse> userResponses ) {
        this.userResponses = userResponses;
    }

    @XmlElementWrapper( name = "links" )
    @XmlElement( name = "link" )
    @XmlJavaTypeAdapter( Link.JaxbAdapter.class )
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks( List<Link> links ) {
        this.links = links;
    }
}
