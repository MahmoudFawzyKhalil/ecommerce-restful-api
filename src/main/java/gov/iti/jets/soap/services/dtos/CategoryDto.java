package gov.iti.jets.soap.services.dtos;

import gov.iti.jets.domain.models.Category;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;

@XmlRootElement( name = "category" )
@XmlType( propOrder = {"id", "name"} )
public class CategoryDto implements Serializable {
    private Integer id;
    private String name;

    public CategoryDto() {
    }

    public CategoryDto( int id, String name ) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto( Category category ) {
        this.id = category.getId();
        this.name = category.getName();
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
