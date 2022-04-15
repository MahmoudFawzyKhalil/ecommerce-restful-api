package gov.iti.jets.domain.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class OrderLineItem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @ManyToOne
    private Product product;

    private int quantity;

    private BigDecimal total;
    @ManyToOne
    @JoinColumn( name = "order_id" )
    private Order order;

    public OrderLineItem() {
    }

    public OrderLineItem( CartLineItem cartLineItem ) {
        this.product = cartLineItem.getProduct();
        this.quantity = cartLineItem.getQuantity();
        this.total = calculateTotal( product, quantity );
    }

    private BigDecimal calculateTotal( Product product, int quantity ) {
        return product.getPrice().multiply( BigDecimal.valueOf( quantity ) );
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal( BigDecimal total ) {
        this.total = total;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder( Order order ) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct( Product product ) {
        this.product = product;
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
    public String toString() {
        return "OrderLineItem{" +
                "id=" + id +
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                ", total=" + total +
                ", order=" + order.getId() +
                '}';
    }
}