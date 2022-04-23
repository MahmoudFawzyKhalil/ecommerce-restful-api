package gov.iti.jets.rest.resources.product;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class ProductFilters {
    @DefaultValue( "%" )
    @QueryParam( "name" )
    private String name;

    public ProductFilters() {
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ProductFilters{" +
                "name='" + name + '\'' +
                '}';
    }
}
