package gov.iti.jets.soap.services;

import gov.iti.jets.domain.models.Category;
import gov.iti.jets.domain.models.Product;
import gov.iti.jets.domain.services.CategoryService;
import gov.iti.jets.domain.services.ProductService;
import gov.iti.jets.soap.exceptions.SOAPApiException;
import gov.iti.jets.soap.services.dtos.CategoryDto;
import gov.iti.jets.soap.services.dtos.ProductCreationDto;
import gov.iti.jets.soap.services.dtos.ProductDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

import java.security.PublicKey;
import java.util.List;

import static java.util.stream.Collectors.toList;

@SuppressWarnings( "NonJaxWsWebServices" )
@WebService( targetNamespace = "http://www.jets.gov.iti.eg/ecommerce",
        name = "InventoryManagement",
        portName = "InventoryManagementPort",
        serviceName = "InventoryManagementService" )

@BindingType( SOAPBinding.SOAP12HTTP_BINDING )
public class InventoryManagement {

    @WebResult( name = "echoedString", partName = "echoedStringPartName" )
    public String echo( @WebParam( name = "stringToEcho", partName = "stringToEchoPartName" ) String s ) {
        return s;
    }


    @WebResult( name = "category" )
    public List<CategoryDto> getAllCategories() {
        return CategoryService.getAllCategories().stream()
                .map( CategoryDto::new )
                .collect( toList() );
    }

    @WebResult( name = "category" )
    public CategoryDto getCategoryById( @WebParam( name = "id" ) int id ) {
        return CategoryService.findCategoryById( id ).map( CategoryDto::new ).orElseThrow( () -> new SOAPApiException(
                String.format( "No category exists with the id (%s).", id ) ) );
    }

    @WebResult( name = "category" )
    public CategoryDto createCategory( @WebParam( name = "category" ) CategoryDto categoryDto ) {
        Category category = new Category( categoryDto.getName() );
        CategoryService.createCategory( category );
        return new CategoryDto( category );
    }

    @WebResult( name = "category" )
    public CategoryDto updateOrCreateCategory( @WebParam( name = "category" ) CategoryDto categoryDto ) {
        CategoryService.updateCategory( new Category( categoryDto.getId(), categoryDto.getName() ) );
        return categoryDto;
    }

    @WebResult( name = "category" )
    public void deleteCategory( @WebParam( name = "id" ) int id ) {
        CategoryService.deleteCategory( id );
    }

    @WebResult( name = "product" )
    public List<ProductDto> getAllProductsForCategory( int id ) {
        return ProductService.getAllProductsForCategory( id )
                .stream().map( ProductDto::new )
                .collect( toList() );
    }

    @WebResult( name = "product" )
    public List<ProductDto> getAllProducts() {
        return ProductService.getAllProducts().stream()
                .map( ProductDto::new )
                .collect( toList() );
    }

    @WebResult( name = "product" )
    public ProductDto createProduct( @WebParam( name = "product" ) ProductCreationDto productCreationDto ) {
        Product product = new Product( productCreationDto.getName(),
                productCreationDto.getDescription(),
                productCreationDto.getQuantity(),
                productCreationDto.getPrice() );
        ProductService.createProduct( product );
        return new ProductDto( product );
    }

    @WebResult( name = "product" )
    public ProductDto getProductById( @WebParam( name = "id" ) int id ) {
        return ProductService.findCategoryById( id ).map( ProductDto::new ).orElseThrow( () ->
                new SOAPApiException( String.format( "No product exists with the id (%s).", id ) ) );
    }

}
