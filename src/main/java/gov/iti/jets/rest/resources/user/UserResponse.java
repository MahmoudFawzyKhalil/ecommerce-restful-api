package gov.iti.jets.rest.resources.user;

import gov.iti.jets.domain.enums.Role;
import gov.iti.jets.domain.models.User;
import gov.iti.jets.rest.utils.LinkJsonbAdapter;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement( name = "user" )
@XmlType( propOrder = {"id", "firstName", "lastName", "email", "role", "links"} )
@JsonbPropertyOrder( {"id", "firstName", "lastName", "email", "role", "links"} )
public class UserResponse implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    @JsonbTypeAdapter( LinkJsonbAdapter.class )
    private List<Link> links = new ArrayList<>();

    public UserResponse() {
    }

    public UserResponse( User user ) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole( Role role ) {
        this.role = role;
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

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", links=" + links +
                '}';
    }

    public void addLink( Link link ) {
        links.add( link );
    }
}
