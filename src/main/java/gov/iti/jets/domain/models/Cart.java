package gov.iti.jets.domain.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cart {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @OneToMany( fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true )
    private Set<CartLineItem> cartLineItems = new HashSet<>();

    @OneToOne
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner( User owner ) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void addLineItemToCart( CartLineItem cartLineItem ) {
        this.cartLineItems.add( cartLineItem );
        cartLineItem.setCart( this );
    }

    public Set<CartLineItem> getLineItems() {
        return cartLineItems;
    }

    public void setLineItems( Set<CartLineItem> cartLineItems ) {
        this.cartLineItems = cartLineItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", cartLineItems=" + cartLineItems +
                ", owner=" + owner.getEmail() +
                '}';
    }

    public void empty() {
        cartLineItems.clear();
    }
}
