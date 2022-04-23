package gov.iti.jets.soap.services.dtos;

import gov.iti.jets.domain.models.Cart;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@XmlRootElement( name = "cart" )
@XmlType( propOrder = {"id", "cartLineItems"} )
public class CartDto implements Serializable {
    private int id;
    private Set<CartLineItemDto> cartLineItems;

    public CartDto() {
    }

    public CartDto( Cart cart ) {
        this.id = cart.getId();
        this.cartLineItems = cart.getLineItems()
                .stream()
                .map( CartLineItemDto::new )
                .collect( toSet() );
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public Set<CartLineItemDto> getCartLineItems() {
        return cartLineItems;
    }

    @XmlElementWrapper(name = "cartLineItems")
    @XmlElement(name = "cartLineItem")
    public void setCartLineItems( Set<CartLineItemDto> cartLineItems ) {
        this.cartLineItems = cartLineItems;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "cartLineItems = " + cartLineItems + ")";
    }
}
