package gov.iti.jets.soap.services.dtos;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategoryCreationDto {
    private String name;

    public CategoryCreationDto() {
    }

    public CategoryCreationDto( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryCreationDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
