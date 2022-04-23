package gov.iti.jets.domain.models;

import gov.iti.jets.domain.enums.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table( name = "orders" )
public class Order {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @OneToMany( mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true )
    private Set<OrderLineItem> orderLineItems;

    @ManyToOne
    private User customer;

    @Enumerated( EnumType.STRING )
    private OrderStatus status;

    private BigDecimal total;

    public Order() {
    }

    public Order( User user ) {
        this.orderLineItems = createOrderLineItemsFromCart( user.getCart() );
        this.total = calculateTotal();
        this.customer = user;
        this.status = OrderStatus.PENDING;
    }

    private BigDecimal calculateTotal() {
        return orderLineItems.stream()
                .map( OrderLineItem::getTotal )
                .reduce( BigDecimal.ZERO, BigDecimal::add );
    }

    private Set<OrderLineItem> createOrderLineItemsFromCart( Cart cart ) {
        return cart.getLineItems().stream()
                .map( OrderLineItem::new )
                .peek( i -> i.setOrder( this ) )
                .collect( Collectors.toSet() );
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer( User maker ) {
        this.customer = maker;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public Set<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems( Set<OrderLineItem> orderLineItems ) {
        this.orderLineItems = orderLineItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus( OrderStatus status ) {
        this.status = status;
    }

    public void addLineItemToOrder( OrderLineItem cartLineItem ) {
        this.orderLineItems.add( cartLineItem );
        cartLineItem.setOrder( this );
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal( BigDecimal total ) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderLineItems=" + orderLineItems +
                ", customer=" + customer.getEmail() +
                ", status=" + status +
                '}';
    }
}
