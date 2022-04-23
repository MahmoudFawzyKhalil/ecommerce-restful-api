package gov.iti.jets.rest.resources.cart;

import gov.iti.jets.domain.models.CartLineItem;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;

@XmlRootElement( name = "cartLineItem" )
public class CartLineItemRequest implements Serializable {
    private int productId;
    private int quantity;

    public CartLineItemRequest() {
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
}
