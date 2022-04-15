package gov.iti.jets.domain.models;

import gov.iti.jets.domain.enums.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "users" )
public class User {

    @OneToMany( mappedBy = "customer" )
    List<Order> orders;
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated( EnumType.STRING )
    private Role role;
    @OneToOne( mappedBy = "owner" )
    private Cart cart;

    public User() {
        orders = new ArrayList<>();
    }

    public User( String firstName, String lastName, String email, Role role ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.orders = new ArrayList<>();
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
