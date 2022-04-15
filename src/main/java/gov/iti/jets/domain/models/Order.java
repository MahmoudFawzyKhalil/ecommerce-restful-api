package gov.iti.jets.domain.models;

import gov.iti.jets.domain.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table( name = "orders" )
public class Order {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @OneToMany( mappedBy = "order", fetch = FetchType.EAGER )
    private Set<OrderLineItem> orderLineItems;

    @ManyToOne
    private User customer;

    @Enumerated( EnumType.STRING )
    private OrderStatus status;

    public Order() {
    }

    public Order( Cart cart ) {
        this.orderLineItems = createOrderLineItemsFromCart( cart );
        this.customer = cart.getOwner();
        this.status = OrderStatus.PENDING;
    }

    private Set<OrderLineItem> createOrderLineItemsFromCart( Cart cart ) {
        return cart.getLineItems().stream().map( OrderLineItem::new ).collect( Collectors.toSet() );
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
