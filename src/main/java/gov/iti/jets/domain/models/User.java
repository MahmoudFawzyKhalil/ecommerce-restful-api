package gov.iti.jets.domain.models;

import gov.iti.jets.domain.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "users" )
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @NotEmpty(message = "you must provide a first name.")
    private String firstName;

    @NotEmpty(message = "you must provide a last name.")
    private String lastName;

    @NotEmpty(message = "you must provide an email.")
    @Email(message = "email is not valid.")
    private String email;

    @Enumerated( EnumType.STRING )
    @NotNull(message = "you must provide a role.")
    private Role role;

    @OneToOne( mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private Cart cart;

    @OneToMany( mappedBy = "customer" )
    List<Order> orders;

    public User() {
    }

    public User( String firstName, String lastName, String email, Role role ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.orders = new ArrayList<>();
        setCart( new Cart() );
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders( List<Order> orders ) {
        this.orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart( Cart cart ) {
        this.cart = cart;
        cart.setOwner( this );
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

    public void addOrderToUser( Order order ) {
        this.orders.add( order );
        order.setCustomer( this );
    }

    @Override
    public String toString() {
        return "User{" +
                "orders=" + orders.size() +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", cart=" + cart.getId() +
                '}';
    }
}
