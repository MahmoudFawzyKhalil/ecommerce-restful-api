package gov.iti.jets.rest.resources.order;

import gov.iti.jets.rest.resources.product.ProductResponse;
import gov.iti.jets.rest.utils.LinkJsonbAdapter;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.List;

@XmlRootElement( name = "orders" )
@XmlType( propOrder = {"orderResponses", "links"} )
@JsonbPropertyOrder( {"orderResponses", "links"} )
public class OrderResponseWrapper {

    @JsonbProperty( "orders" )
    private List<OrderResponse> orderResponses;
    @JsonbTypeAdapter( LinkJsonbAdapter.class )
    private List<Link> links;

    public OrderResponseWrapper() {
    }

    public OrderResponseWrapper( List<OrderResponse> orderResponses, List<Link> links ) {
        this.orderResponses = orderResponses;
        this.links = links;
    }

    @XmlElement( name = "order" )
    public List<OrderResponse> getOrderResponses() {
        return orderResponses;
    }

    public void setOrderResponses( List<OrderResponse> orderResponses ) {
        this.orderResponses = orderResponses;
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
}
