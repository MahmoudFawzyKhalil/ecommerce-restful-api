package gov.iti.jets.rest.resources.category;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import gov.iti.jets.domain.models.Product;
import gov.iti.jets.domain.services.ProductService;
import gov.iti.jets.rest.exceptions.ApiException;
import gov.iti.jets.rest.resources.product.ProductResource;
import gov.iti.jets.rest.resources.product.ProductResponse;
import gov.iti.jets.rest.resources.product.ProductResponseWrapper;
import gov.iti.jets.rest.utils.ApiUtils;
import gov.iti.jets.rest.beans.PaginationData;
import gov.iti.jets.domain.models.Category;
import gov.iti.jets.domain.services.CategoryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import static java.util.stream.Collectors.*;

@Path( "categories" )
@Consumes( {"application/json; qs=1", "application/xml; qs=0.75"} )
@Produces( {"application/json; qs=1", "application/xml; qs=0.75"} )
public class CategoryResource {
    @Context
    private UriInfo uriInfo;

    @GET
    public Response getAllCategories( @BeanParam PaginationData paginationData, @BeanParam CategoryFilters filters, @QueryParam( "fields" ) List<String> fieldsWanted ) {

        List<CategoryResponse> categoryResponses = CategoryService.findCategoryResponses( paginationData, filters );

        categoryResponses.forEach( cr -> addLinksToCategoryResponse( cr, uriInfo ) );

        ApiUtils.nullifyListFieldsForPartialResponse( categoryResponses, fieldsWanted, CategoryResponse.class );

        List<Link> links = ApiUtils.createPaginatedResourceLinks( uriInfo, paginationData, CategoryService.getNumberOfCategories() );

        CategoryResponseWrapper categoryResponseWrapper = new CategoryResponseWrapper( categoryResponses, links );

        return Response.ok().entity( categoryResponseWrapper ).build();
    }

    public static void addLinksToCategoryResponse( CategoryResponse categoryResponse, UriInfo uriInfo ) {
        categoryResponse.addLink( ApiUtils.createSelfLink( uriInfo, categoryResponse.getId(), CategoryResource.class ) );

        Link products = Link.fromUriBuilder( uriInfo.getBaseUriBuilder()
                .path( CategoryResource.class )
                .path( String.valueOf( categoryResponse.getId() ) )
                .path( ProductResource.class ) ).rel( "products" ).build();

        categoryResponse.addLink( products );
    }

    @POST
    public Response createCategory( CategoryRequest categoryRequest ) {
        CategoryValidator.validate( categoryRequest );

        Category category = new Category( categoryRequest.getName() );

        CategoryService.createCategory( category );

        CategoryResponse categoryResponse = new CategoryResponse( category.getId(), category.getName() );
        addLinksToCategoryResponse( categoryResponse, uriInfo );

        URI createdAtUri = ApiUtils.getCreatedAtUriForPostRequest( uriInfo, categoryResponse.getId() );

        return Response.created( createdAtUri ).entity( categoryResponse ).build();
    }

    @GET
    @Path( "{id}" )
    public Response findCategoryById( @PathParam( "id" ) int id ) {
        Category category = CategoryService.findCategoryById( id ).orElseThrow( () -> new ApiException(
                String.format( "No category exists with the id (%s)", id ), 400 ) );
        CategoryResponse categoryResponse = new CategoryResponse( category );
        addLinksToCategoryResponse( categoryResponse, uriInfo );
        return Response.ok().entity( categoryResponse ).build();
    }

    @DELETE
    @Path( "{id}" )
    public Response deleteCategory( @PathParam( "id" ) int id ) {
        CategoryService.deleteCategory( id );
        return Response.noContent().build();
    }

    @PUT
    @Path( "{id}" )
    public Response updateCategory( @PathParam( "id" ) int id, CategoryRequest categoryRequest ) {
        CategoryValidator.validate( categoryRequest );

        Category category = new Category( categoryRequest.getName() );
        category.setId( id );

        CategoryService.updateCategory( category );

        CategoryResponse categoryResponse = new CategoryResponse( category.getId(), category.getName() );

        addLinksToCategoryResponse( categoryResponse, uriInfo );

        return Response.ok().entity( categoryResponse ).build();
    }

    @GET
    @Path( "{id}/products" )
    public Response getAllProductsForCategory( @PathParam( "id" ) int id ) {
        List<ProductResponse> productResponses = ProductService.getAllProductsForCategory( id )
                .stream()
                .map( ProductResponse::new )
                .collect( toList() );

        if ( productResponses.isEmpty() )
            throw new ApiException( String.format( "No products exist for category with id (%s). Does this category exist?", id ), 400 );

        productResponses.forEach( pr -> ProductResource.addLinksToProductResponse( pr, uriInfo ) );
        Link self = ApiUtils.createAbsoluteSelfLink( uriInfo );
        ProductResponseWrapper productResponseWrapper = new ProductResponseWrapper( productResponses, List.of( self ) );

        return Response.ok().entity( productResponseWrapper ).build();
    }
}
