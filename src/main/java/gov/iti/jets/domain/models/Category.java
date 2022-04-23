package gov.iti.jets.domain.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;
    @NotEmpty( message = "Please provide a name for the category." )
    private String name;
    @ManyToMany( mappedBy = "categories" )
    private Set<Product> products = new HashSet<>();

    public Category() {
    }

    public Category( String name ) {
        this.name = name;
    }

    public Category( Integer id, String name ) {
        this.id = id;
        this.name = name;
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts( Set<Product> products ) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name='" + name + '\'' + ", products=" + products.size() + '}';
    }
}