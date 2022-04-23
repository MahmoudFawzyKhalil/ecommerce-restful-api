package gov.iti.jets.soap.services.dtos;

import gov.iti.jets.domain.models.Category;
import gov.iti.jets.domain.models.Product;
import gov.iti.jets.rest.resources.category.CategoryResponse;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@XmlRootElement( name = "product" )
@XmlType( propOrder = {"id", "name", "description", "quantity", "price", "categories"} )
@JsonbPropertyOrder( {"id", "name", "description", "quantity", "price", "categories"} )
public class ProductDto implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private Set<CategoryDto> categories;

    public ProductDto() {
    }

    public ProductDto( Product product ) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
        this.categories = product.getCategories().stream()
                .map( CategoryDto::new )
                .collect( toSet() );
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

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity( Integer quantity ) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice( BigDecimal price ) {
        this.price = price;
    }

    @XmlElementWrapper( name = "categories" )
    @XmlElement( name = "category" )
    public Set<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories( Set<CategoryDto> categories ) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "quantity = " + quantity + ", " +
                "price = " + price + ", " +
                "categories = " + categories.size() + ")";
    }
}
