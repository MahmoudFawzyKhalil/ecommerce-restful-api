package gov.iti.jets.rest.resources.category;

import java.net.URI;
import java.util.List;

import gov.iti.jets.rest.exceptions.ApiException;
import gov.iti.jets.rest.utils.ApiUtils;
import gov.iti.jets.rest.beans.PaginationData;
import gov.iti.jets.domain.models.Category;
import gov.iti.jets.domain.services.CategoryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path( "categories" )
@Consumes( {"application/json; qs=1", "application/xml; qs=0.75"} )
@Produces( {"application/json; qs=1", "application/xml; qs=0.75"} )
public class CategoryResource {
    @Context
    private UriInfo uriInfo;

    @GET
    public Response getAllCategories( @BeanParam PaginationData paginationData, @BeanParam CategoryFilters filters, @QueryParam( "fields" ) List<String> fieldsWanted ) {

        List<CategoryResponse> categoryResponses = CategoryService.findCategoryResponses( paginationData, filters );

        categoryResponses.forEach( this::addLinksToCategoryResponse );

        ApiUtils.nullifyListFieldsForPartialResponse( categoryResponses, fieldsWanted, CategoryResponse.class );

        List<Link> links = ApiUtils.createPaginatedResourceLinks( uriInfo, paginationData, CategoryService.getNumberOfCategories() );

        CategoryResponseWrapper categoryResponseWrapper = new CategoryResponseWrapper( categoryResponses, links );

        return Response.ok().entity( categoryResponseWrapper ).build();
    }

    private void addLinksToCategoryResponse( CategoryResponse categoryResponse ) {
        addLinksToCategoryResponse( categoryResponse, uriInfo );
    }

    public static void addLinksToCategoryResponse( CategoryResponse categoryResponse, UriInfo uriInfo ) {
        categoryResponse.addLink( ApiUtils.createSelfLink( uriInfo, categoryResponse.getId(), CategoryResource.class ) );
    }

    @POST
    public Response createCategory( CategoryRequest categoryRequest ) {
        CategoryValidator.ensureCategoryRequestIsValid( categoryRequest );

        Category category = new Category( categoryRequest.getName() );

        CategoryService.createNewCategory( category );

        CategoryResponse categoryResponse = new CategoryResponse( category.getId(), category.getName() );
        addLinksToCategoryResponse( categoryResponse );

        URI createdAtUri = ApiUtils.getCreatedAtUriForPostRequest( uriInfo, categoryResponse.getId() );

        return Response.created( createdAtUri ).entity( categoryResponse ).build();
    }

    @GET
    @Path( "{id}" )
    public Response findCategoryById( @PathParam( "id" ) int id ) {
        Category category = CategoryService.findCategoryById( id ).orElseThrow( () -> new ApiException(
                String.format( "No category exists with the id (%s)", id ), 400 ) );
        CategoryResponse categoryResponse = new CategoryResponse( category );
        addLinksToCategoryResponse( categoryResponse );
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
        CategoryValidator.ensureCategoryRequestIsValid( categoryRequest );

        Category category = new Category( categoryRequest.getName() );
        category.setId( id );

        CategoryService.updateCategory( category );

        CategoryResponse categoryResponse = new CategoryResponse( category.getId(), category.getName() );

        addLinksToCategoryResponse( categoryResponse );

        return Response.ok().entity( categoryResponse ).build();
    }
}
