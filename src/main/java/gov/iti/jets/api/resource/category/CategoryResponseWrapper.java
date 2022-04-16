package gov.iti.jets.api.resource.category;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement( name = "categories" )
public class CategoryResponseWrapper {

    @JsonbProperty( "categories" )
    private List<CategoryResponse> categoryResponses;

    public CategoryResponseWrapper() {

    }

    public CategoryResponseWrapper( List<CategoryResponse> categoryResponses ) {
        this.categoryResponses = categoryResponses;
    }

    @XmlElement( name = "category" )
    public List<CategoryResponse> getCategoryResponses() {
        return categoryResponses;
    }

    public void setCategoryResponses( List<CategoryResponse> categoryResponses ) {
        this.categoryResponses = categoryResponses;
    }
}
