package gov.iti.jets.api.resource.category;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "category")
public class CategoryRequest implements Serializable {
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
