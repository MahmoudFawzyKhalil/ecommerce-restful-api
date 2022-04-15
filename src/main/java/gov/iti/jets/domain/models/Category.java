package gov.iti.jets.domain.models;


import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement // TODO remove annotations
public class Category {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;
    private String name;
    @ManyToMany( mappedBy = "categories" )
    @JsonbTransient //TODO remove annotations
    private Set<Product> products;

    public Category( String name ) {
        this.name = name;
        this.products = new HashSet<>();
    }

    public Category() {
        this.products = new HashSet<>();
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
//    @XmlElementWrapper
    @XmlTransient
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts( Set<Product> products ) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products.size() +
                '}';
    }
}