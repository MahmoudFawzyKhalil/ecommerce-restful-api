package gov.iti.jets.soap.services.dtos;

import gov.iti.jets.domain.models.CartLineItem;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;

@XmlRootElement( name = "cartLineItem" )
@XmlType( propOrder = {"productId", "quantity"} )
public class CartLineItemRequestDto implements Serializable {
    private int productId;
    private int quantity;

    public CartLineItemRequestDto() {
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
                "productIdId = " + productId + ", " +
                "quantity = " + quantity + ")";
    }
}
