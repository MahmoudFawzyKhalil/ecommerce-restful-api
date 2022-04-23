package gov.iti.jets.soap.services.dtos;

import gov.iti.jets.domain.enums.OrderStatus;
import gov.iti.jets.domain.models.Order;
import gov.iti.jets.rest.utils.LinkJsonbAdapter;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@XmlRootElement( name = "order" )
@XmlType( propOrder = {"id", "status", "total", "orderLineItems"} )
public class OrderDto implements Serializable {
    private int id;
    private Set<OrderLineItemDto> orderLineItems;
    private OrderStatus status;
    private BigDecimal total;

    public OrderDto() {
    }

    public OrderDto( Order order ) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.total = order.getTotal();
        this.orderLineItems = order.getOrderLineItems()
                .stream()
                .map( OrderLineItemDto::new )
                .collect( toSet() );
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public Set<OrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems( Set<OrderLineItemDto> orderLineItems ) {
        this.orderLineItems = orderLineItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus( OrderStatus status ) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal( BigDecimal total ) {
        this.total = total;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "orderLineItems = " + orderLineItems + ", " +
                "status = " + status + ", " +
                "total = " + total + ")";
    }
}
