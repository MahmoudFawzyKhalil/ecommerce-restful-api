package gov.iti.jets.api.resource.category;

import gov.iti.jets.domain.models.Category;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.List;

@XmlRootElement
public class CategoryResponse {
    private String name;

    public CategoryResponse() {
    }

    public CategoryResponse( Category category ) {
        this.name = category.getName();
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    private List<Link> links;

    @XmlJavaTypeAdapter( Link.JaxbAdapter.class )
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks( List<Link> links ) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "CategoryResponse[" +
                "name='" + name + '\'' +
                ']';
    }
}
