package gov.iti.jets.rest.resources.category;

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

@XmlRootElement( name = "categories" )
@XmlType( propOrder = {"categoryResponses", "links"} )
@JsonbPropertyOrder( {"categoryResponses", "links"} )
public class CategoryResponseWrapper {

    @JsonbProperty( "categories" )
    private List<CategoryResponse> categoryResponses;
    @JsonbTypeAdapter( LinkJsonbAdapter.class )
    private List<Link> links;

    public CategoryResponseWrapper() {
    }

    public CategoryResponseWrapper( List<CategoryResponse> categoryResponses, List<Link> links ) {
        this.categoryResponses = categoryResponses;
        this.links = links;
    }

    @XmlElement( name = "category" )
    public List<CategoryResponse> getCategoryResponses() {
        return categoryResponses;
    }

    public void setCategoryResponses( List<CategoryResponse> categoryResponses ) {
        this.categoryResponses = categoryResponses;
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
