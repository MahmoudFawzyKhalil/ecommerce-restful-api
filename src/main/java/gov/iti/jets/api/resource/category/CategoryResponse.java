package gov.iti.jets.api.resource.category;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;

@XmlRootElement( name = "category" )
@XmlType( propOrder = {"id", "name"} )
@JsonbPropertyOrder( {"id", "name"} )
public class CategoryResponse implements Serializable {
    private Integer id;
    private String name;

    public CategoryResponse() {
    }

    public CategoryResponse( int id, String name ) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
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
                "id = " + id + ", " +
                "name = " + name + ")";
    }
}
