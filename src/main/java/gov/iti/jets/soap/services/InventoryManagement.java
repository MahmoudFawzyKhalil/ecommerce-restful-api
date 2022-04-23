package gov.iti.jets.soap.services;

import gov.iti.jets.domain.models.Category;
import gov.iti.jets.domain.models.Product;
import gov.iti.jets.domain.services.CategoryService;
import gov.iti.jets.domain.services.ProductService;
import gov.iti.jets.soap.exceptions.SOAPApiException;
import gov.iti.jets.soap.services.dtos.CategoryDto;
import gov.iti.jets.soap.services.dtos.ProductDto;
import gov.iti.jets.soap.services.dtos.ProductRequestDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

import java.util.List;

import static java.util.stream.Collectors.toList;


@SuppressWarnings( "NonJaxWsWebServices" )
@WebService( targetNamespace = "http://www.jets.gov.iti.eg/ecommerce",
        name = "InventoryManagement",
        portName = "InventoryManagementPort",
        serviceName = "InventoryManagementService" )

@BindingType( SOAPBinding.SOAP12HTTP_BINDING )
public class InventoryManagement {

    @WebMethod
    @WebResult( name = "echoedString", partName = "echoedStringPartName" )
    public String echo( @WebParam( name = "stringToEcho", partName = "stringToEchoPartName" ) String s ) {
        return s;
    }

    @WebMethod
    @WebResult( name = "category" )
    public List<CategoryDto> getAllCategories() {
        return CategoryService.getAllCategories().stream()
                .map( CategoryDto::new )
                .collect( toList() );
    }

    @WebMethod
    @WebResult( name = "category" )
    public CategoryDto getCategoryById( @WebParam( name = "id" ) int id ) {
        return CategoryService.findCategoryById( id ).map( CategoryDto::new ).orElseThrow( () -> new SOAPApiException(
                String.format( "No category exists with the id (%s).", id ) ) );
    }

    @WebMethod
    @WebResult( name = "category" )
    public CategoryDto createOrUpdateCategory( @WebParam( name = "category" ) CategoryDto categoryDto ) {
        CategoryService.updateCategory( new Category( categoryDto.getId(), categoryDto.getName() ) );
        return categoryDto;
    }

    @WebMethod
    @WebResult( name = "category" )
    public void deleteCategory( @WebParam( name = "id" ) int id ) {
        CategoryService.deleteCategory( id );
    }

    @WebMethod
    @WebResult( name = "product" )
    public List<ProductDto> getAllProductsForCategory( int id ) {
        return ProductService.getAllProductsForCategory( id )
                .stream().map( ProductDto::new )
                .collect( toList() );
    }

    @WebMethod
    @WebResult( name = "product" )
    public List<ProductDto> getAllProducts() {
        return ProductService.getAllProducts().stream()
                .map( ProductDto::new )
                .collect( toList() );
    }

    @WebMethod
    @WebResult( name = "product" )
    public ProductDto getProductById( @WebParam( name = "id" ) int id ) {
        return ProductService.findCategoryById( id ).map( ProductDto::new ).orElseThrow( () ->
                new SOAPApiException( String.format( "No product exists with the id (%s).", id ) ) );
    }

    @WebMethod
    @WebResult( name = "product" )
    public ProductDto createOrUpdateProduct( @WebParam( name = "product" ) ProductRequestDto productRequestDto ) {
        if ( productRequestDto == null )
            throw new SOAPApiException( "You must provide a valid soap:Body" );

        Product product = new Product( productRequestDto.getName(),
                productRequestDto.getDescription(),
                productRequestDto.getQuantity(),
                productRequestDto.getPrice() );
        product.setId( productRequestDto.getId() );

        ProductService.updateProduct( product );

        return new ProductDto( product );
    }


    @WebMethod
    @WebResult( name = "product" )
    public void deleteProduct( @WebParam( name = "id" ) int id ) {
        ProductService.deleteProduct( id );
    }

    @WebMethod
    @WebResult( name = "product" )
    public ProductDto addCategoryToProduct( @WebParam( name = "productId" ) int productId,
                                            @WebParam( name = "categoryId" ) int categoryId ) {
        Product product = ProductService.addCategoryToProduct( productId, categoryId );
        return new ProductDto( product );
    }

    @WebMethod
    @WebResult( name = "product" )
    public ProductDto removeCategoryFromProduct( @WebParam( name = "productId" ) int productId,
                                                 @WebParam( name = "categoryId" ) int categoryId ) {
        Product product = ProductService.deleteCategoryFromProduct( productId, categoryId );
        return new ProductDto( product );
    }
}
