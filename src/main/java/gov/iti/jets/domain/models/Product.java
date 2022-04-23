package gov.iti.jets.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @NotEmpty( message = "you must provide a name for the product." )
    private String name;

    private String description;
    @Min( message = "you must provide a zero or positive quantity.", value = 0 )
    @NotNull( message = "you must provide a quantity." )
    private int quantity;

    @DecimalMin( message = "you must provide a positive price value.", value = "0.01" )
    @NotNull( message = "you must provide a price." )
    private BigDecimal price;

    @ManyToMany( fetch = FetchType.EAGER )
    private Set<Category> categories = new HashSet<>();

    public Product() {

    }

    public Product( String name, String description, int quantity, BigDecimal price ) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice( BigDecimal price ) {
        this.price = price;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories( Set<Category> categories ) {
        this.categories = categories;
    }

    public void addCategoryToProduct( Category category ) {
        this.categories.add( category );
        category.getProducts().add( this );
    }

    public void removeCategoryFromProduct( Category category ) {
        this.categories.remove( category );
        category.getProducts().remove( this );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", categories=" + categories.size() +
                '}';
    }
}