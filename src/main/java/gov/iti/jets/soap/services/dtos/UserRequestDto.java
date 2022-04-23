package gov.iti.jets.soap.services.dtos;

import gov.iti.jets.domain.enums.Role;
import gov.iti.jets.domain.models.User;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;

@XmlRootElement( name = "user" )
@XmlType( propOrder = {"id", "firstName", "lastName", "email", "role"} )
public class UserRequestDto implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    public UserRequestDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
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

}
