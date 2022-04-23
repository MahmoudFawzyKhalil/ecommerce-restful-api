package gov.iti.jets.rest.resources.cart;

import gov.iti.jets.domain.models.CartLineItem;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;

@XmlRootElement( name = "cartLineItem" )
@XmlType( propOrder = {"id", "productId", "quantity"} )
@JsonbPropertyOrder( {"id", "productId", "quantity"} )
public class CartLineItemResponse implements Serializable {
    private int id;
    private int productId;
    private int quantity;

    public CartLineItemResponse() {
    }

    public CartLineItemResponse( CartLineItem cartLineItem ) {
        this.id = cartLineItem.getId();
        this.productId = cartLineItem.getProduct().getId();
        this.quantity = cartLineItem.getQuantity();
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "productIdId = " + productId + ", " +
                "quantity = " + quantity + ")";
    }
}
