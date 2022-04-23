package gov.iti.jets.rest.resources.category;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class CategoryFilters {
    @DefaultValue( "%" )
    @QueryParam( "name" )
    private String name;

    public CategoryFilters() {
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryFilters{" +
                "name='" + name + '\'' +
                '}';
    }
}
