package gov.iti.jets.api.resource.category;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import gov.iti.jets.api.utils.ApiUtils;
import gov.iti.jets.api.beans.PaginationData;
import gov.iti.jets.domain.models.Category;
import gov.iti.jets.domain.services.CategoryService;
import gov.iti.jets.persistence.CategoryRepository;
import gov.iti.jets.persistence.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path( "categories" )
@Consumes( {"application/json; qs=1", "application/xml; qs=0.75"} )
@Produces( {"application/json; qs=1", "application/xml; qs=0.75"} )
public class CategoryResource {
    private final CategoryRepository cr;
    private final EntityManager em;
    private final EntityTransaction tx;
    private final List<String> allCategoryFields = new ArrayList<>( List.of( "id", "name" ) );

    @Context
    private UriInfo uriInfo;

    {
        em = JpaUtil.createEntityManager();
        cr = new CategoryRepository( em );
        tx = em.getTransaction();
        tx.begin();
    }

    @GET
    public Response getAllCategories( @BeanParam PaginationData paginationData,
                                      @BeanParam CategoryFilters filters,
                                      @QueryParam( "fields" ) List<String> fields ) {
        List<CategoryResponse> categoryResponses = CategoryService.findCategoryResponses( paginationData, filters );
        if ( fields != null && !fields.isEmpty() )
            convertToPartialResponse( categoryResponses, fields );

        CategoryResponseWrapper categoryResponseWrapper = new CategoryResponseWrapper( categoryResponses );
        Link[] links = createGetAllCategoriesLinks( paginationData );
        return Response.ok()
                .entity( categoryResponseWrapper )
                .links( links )
                .build();
    }

    private void convertToPartialResponse( List<CategoryResponse> categories, List<String> fields ) {
        List<String> fieldsToNullify = ApiUtils.getFieldsToNullifyForPartialResponse( allCategoryFields, fields );

        for ( var category : categories ) {
            for ( String field : fieldsToNullify ) {
                switch ( field ) {
                    case "id":
                        category.setId( null );
                        break;
                    case "name":
                        category.setName( null );
                }
            }
        }
    }

    private Link[] createGetAllCategoriesLinks( PaginationData paginationData ) {
        long numberOfRecords = CategoryService.getNumberOfCategories();
        int nextOffset = paginationData.getOffset() + paginationData.getLimit();
        int previousOffset = paginationData.getOffset() - paginationData.getLimit();

        Link self = Link.fromUriBuilder( uriInfo.getAbsolutePathBuilder() ).rel( "self" ).build();
        Link nextPage = ApiUtils.createNextPageLink( uriInfo, paginationData, nextOffset );
        Link previousPage = ApiUtils.createPreviousPageLink( uriInfo, paginationData, previousOffset );

        List<Link> links = new ArrayList<>();
        links.add( self );
        if ( nextOffset < numberOfRecords )
            links.add( nextPage );
        if ( previousOffset > 0 )
            links.add( previousPage );

        return links.toArray( new Link[]{} );
    }

    @POST
    public Response createCategory( CategoryRequest categoryRequest ) {
        CategoryValidator.ensureCategoryRequestIsValidForCreate(categoryRequest);
        System.out.println(categoryRequest);
        Category category = new Category( categoryRequest.getName() );
        CategoryService.createNewCategory( category );
        CategoryResponse categoryResponse = new CategoryResponse( category.getId(), category.getName() );
        URI createdAtUri = ApiUtils.getCreatedAtUriForPostRequest( uriInfo, categoryResponse.getId() );
        return Response.created( createdAtUri ).entity( categoryResponse ).build();
    }
//    @GET
//    @Path( "{id}" )
//    public Response readCategoryById( @PathParam( "id" ) int id ) {
//        Category category = cr.findOne( id ).orElseThrow( NotFoundException::new );
//        CategoryResponse categoryResponse = new CategoryResponse( category );
//        em.close();
//
//        Link self = Link.fromUriBuilder( uriInfo.getAbsolutePathBuilder() ).rel( "self" ).build();
//
//        categoryResponse.setLinks( Arrays.asList( self ) );
//
//        return Response.ok().entity( categoryResponse ).build();
//    }

    @DELETE
    @Path( "{id}" )
    public Response deleteCategory( @PathParam( "id" ) int id ) {
        cr.findOne( id ).ifPresentOrElse( cr::delete, () -> {
            throw new NotFoundException();
        } );
        tx.commit();
        em.close();
        return Response.noContent().build();
    }

    @PUT
    @Path( "{id}" )
    public Response updateCategory( @PathParam( "id" ) int id, Category updatedCategory ) {
        updatedCategory.setId( id );
        cr.update( updatedCategory );
        tx.commit();
        em.close();
        return Response.ok().entity( updatedCategory ).build();
    }

    // TODO get all categories for product
    // @GET
    // @Path("{id}/products")
    // @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    // @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    // public Response getAllProductsForCategory(@PathParam("id") int id) {
    //     var products = em.createQuery("SELECT c.products FROM Category c", Product.class)
    //             .getResultList();

    //     if (products.isEmpty())
    //         throw new NotFoundException();

    //     tx.commit();
    //     em.close();
    //     return Response.ok().entity(products).build();
    // }
}
