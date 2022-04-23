package gov.iti.jets.rest.resources.category;

import gov.iti.jets.domain.models.Category;
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

@XmlRootElement( name = "category" )
@XmlType( propOrder = {"id", "name", "links"} )
@JsonbPropertyOrder( {"id", "name", "links"} )
public class CategoryResponse implements Serializable {
    private Integer id;
    private String name;
    @JsonbTypeAdapter( LinkJsonbAdapter.class )
    private List<Link> links = new ArrayList<>();

    public CategoryResponse() {
    }

    public CategoryResponse( int id, String name ) {
        this.id = id;
        this.name = name;
    }

    public CategoryResponse( Category category ) {
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

    @XmlElementWrapper( name = "links" )
    @XmlElement( name = "link" )
    @XmlJavaTypeAdapter( Link.JaxbAdapter.class )
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks( List<Link> links ) {
        this.links = links;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ")";
    }

    public void addLink( Link link ) {
        links.add( link );
    }
}
