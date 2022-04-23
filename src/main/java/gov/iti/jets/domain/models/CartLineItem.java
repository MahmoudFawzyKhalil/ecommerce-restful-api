package gov.iti.jets.domain.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class CartLineItem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @ManyToOne
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn( name = "cart_id" )
    private Cart cart;

    public CartLineItem() {
    }

    public CartLineItem( Product product, int quantity ) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct( Product product ) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart( Cart cart ) {
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        CartLineItem that = (CartLineItem) o;
        return product.equals( that.product );
    }

    @Override
    public int hashCode() {
        return Objects.hash( product );
    }

    @Override
    public String toString() {
        return "CartLineItem{" +
                "id=" + id +
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                ", cart=" + cart.getId() +
                '}';
    }
}