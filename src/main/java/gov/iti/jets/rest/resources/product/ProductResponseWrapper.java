package gov.iti.jets.rest.resources.product;

import gov.iti.jets.rest.resources.category.CategoryResponse;
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

@XmlRootElement( name = "products" )
@XmlType( propOrder = {"productResponses", "links"} )
@JsonbPropertyOrder( {"productResponses", "links"} )
public class ProductResponseWrapper {

    @JsonbProperty( "products" )
    private List<ProductResponse> productResponses;
    @JsonbTypeAdapter( LinkJsonbAdapter.class )
    private List<Link> links;

    public ProductResponseWrapper() {
    }

    public ProductResponseWrapper( List<ProductResponse> productResponses, List<Link> links ) {
        this.productResponses = productResponses;
        this.links = links;
    }

    @XmlElement( name = "product" )
    public List<ProductResponse> getProductResponses() {
        return productResponses;
    }

    public void setProductResponses( List<ProductResponse> productResponses ) {
        this.productResponses = productResponses;
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
