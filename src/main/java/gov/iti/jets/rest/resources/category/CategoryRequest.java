package gov.iti.jets.rest.resources.category;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement( name = "category" )
public class CategoryRequest implements Serializable {
    @NotNull(message = "you must provide a name for the category.")
    private String name;

    public CategoryRequest() {
    }

    public CategoryRequest( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ")";
    }
}
