package gov.iti.jets.rest.resources.order;

import gov.iti.jets.domain.enums.OrderStatus;
import gov.iti.jets.domain.models.Order;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@XmlRootElement( name = "order" )
@XmlType( propOrder = {"id", "status", "total", "orderLineItems", "links"} )
@JsonbPropertyOrder( {"id", "status", "total", "orderLineItems", "links"} )
public class OrderResponse implements Serializable {
    private int id;
    private Set<OrderLineItemResponse> orderLineItems;
    private OrderStatus status;
    private BigDecimal total;
    @JsonbTypeAdapter( LinkJsonbAdapter.class )
    private List<Link> links = new ArrayList<>();

    public OrderResponse() {
    }

    public OrderResponse( Order order ) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.total = order.getTotal();
        this.orderLineItems = order.getOrderLineItems()
                .stream()
                .map( OrderLineItemResponse::new )
                .collect( toSet() );
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public Set<OrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems( Set<OrderLineItemResponse> orderLineItems ) {
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


    @XmlElementWrapper( name = "links" )
    @XmlElement( name = "link" )
    @XmlJavaTypeAdapter( Link.JaxbAdapter.class )
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks( List<Link> links ) {
        this.links = links;
    }

    public void addLink( Link link ) {
        this.links.add( link );
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
