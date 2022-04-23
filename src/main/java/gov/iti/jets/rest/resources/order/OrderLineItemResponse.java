package gov.iti.jets.rest.resources.order;

import gov.iti.jets.domain.models.OrderLineItem;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement( name = "orderLineItem" )
@XmlType( propOrder = {"id", "productId", "quantity", "total"} )
@JsonbPropertyOrder( {"id", "productId", "quantity", "total"} )
public class OrderLineItemResponse implements Serializable {
    private int id;
    private int productId;
    private int quantity;
    private BigDecimal total;

    public OrderLineItemResponse() {
    }

    public OrderLineItemResponse( OrderLineItem orderLineItem ) {
        this.id = orderLineItem.getId();
        this.productId = orderLineItem.getProduct().getId();
        this.quantity = orderLineItem.getQuantity();
        this.total = orderLineItem.getTotal();
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId( int productId ) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
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
                "productId = " + productId + ", " +
                "quantity = " + quantity + ", " +
                "total = " + total + ")";
    }
}
