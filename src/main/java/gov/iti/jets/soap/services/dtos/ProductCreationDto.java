package gov.iti.jets.soap.services.dtos;

import gov.iti.jets.domain.models.Product;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@XmlRootElement( name = "product" )
@XmlType( propOrder = {"name", "description", "quantity", "price"} )
public class ProductCreationDto implements Serializable {
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;

    public ProductCreationDto() {
    }

    public ProductCreationDto( Product product ) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
    }

    @XmlElement( required = true )
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

    @XmlElement( required = true )
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity( Integer quantity ) {
        this.quantity = quantity;
    }

    @XmlElement( required = true )
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice( BigDecimal price ) {
        this.price = price;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "quantity = " + quantity + ", " +
                "price = " + price + ", ";
    }
}
