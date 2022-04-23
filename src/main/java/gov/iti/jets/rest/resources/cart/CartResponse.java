package gov.iti.jets.rest.resources.cart;

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
@XmlType( propOrder = {"id", "cartLineItems", "links"} )
@JsonbPropertyOrder( {"id", "cartLineItems", "links"} )
public class CartResponse implements Serializable {
    private int id;
    private Set<CartLineItemResponse> cartLineItems;
    @JsonbTypeAdapter( LinkJsonbAdapter.class )
    private List<Link> links = new ArrayList<>();

    public CartResponse() {
    }

    public CartResponse( Cart cart ) {
        this.id = cart.getId();
        this.cartLineItems = cart.getLineItems()
                .stream()
                .map( CartLineItemResponse::new )
                .collect( toSet() );
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public Set<CartLineItemResponse> getCartLineItems() {
        return cartLineItems;
    }

    @XmlElementWrapper(name = "cartLineItems")
    @XmlElement(name = "cartLineItem")
    public void setCartLineItems( Set<CartLineItemResponse> cartLineItems ) {
        this.cartLineItems = cartLineItems;
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
        links.add( link );
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "cartLineItems = " + cartLineItems + ")";
    }
}
