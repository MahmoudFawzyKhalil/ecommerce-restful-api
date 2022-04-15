package gov.iti.jets.api.resource.product;

import java.net.URI;
import java.util.List;

import gov.iti.jets.domain.models.Product;
import gov.iti.jets.persistence.CategoryRepository;
import gov.iti.jets.persistence.JpaUtil;
import gov.iti.jets.persistence.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.*;

@Path( "products" )
@Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML} )
@Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML} )
public class ProductResource {
    private final ProductRepository pr;
    private final CategoryRepository cr;
    private final EntityManager em;
    private final EntityTransaction tx;
    @Context
    private UriInfo uriInfo;

    {
        em = JpaUtil.createEntityManager();
        pr = new ProductRepository( em );
        cr = new CategoryRepository( em );
        tx = em.getTransaction();
        tx.begin();
    }

    private URI getCreatedUri( int id ) {
        return URI.create( String.format( "%s/%s", uriInfo.getPath(), id ) );
    }

    @POST
    public Response createCategory( Product product ) {
        pr.create( product );
        tx.commit();
        em.close();
        return Response.created( getCreatedUri( product.getId() ) ).entity( product ).build();
    }

    /*
     * /products/{id}/categories
     * xPOST: adds a new category for that product
     */

    @POST
    @Path( "{pid}/categories" )
    public Response addCategoryToProduct( @PathParam( "pid" ) int pid, int cid ) {
        var product = pr.findOne( pid ).orElseThrow( NotFoundException::new );
        var category = cr.findOne( cid ).orElseThrow( NotFoundException::new );

        product.addCategoryToProduct( category );

        pr.update( product );

        tx.commit();
        em.close();
        return Response.created( getCreatedUri( product.getId() ) ).entity( product ).build();
    }

    @DELETE
    @Path( "{pid}/categories/{cid}" )
    public Response removeCategoryFromProduct( @PathParam( "pid" ) int pid, @PathParam( "cid" ) int cid ) {
        var product = pr.findOne( pid ).orElseThrow( NotFoundException::new );
        var category = cr.findOne( cid ).orElseThrow( NotFoundException::new );

        product.removeCategoryFromProduct( category );

        pr.update( product );

        tx.commit();
        em.close();
        return Response.noContent().build();
    }

    @GET
    @Path( "{id}" )
    public Response readProductById( @PathParam( "id" ) int id ) {
        Product product = pr.findOne( id ).orElseThrow( NotFoundException::new );
        em.close();
        return Response.ok().entity( product ).build();
    }

    @GET
    public Response readAllProducts( @DefaultValue( "%" ) @QueryParam( "q" ) String query ) {
        var products = em.createQuery( "SELECT p FROM Product p WHERE p.name LIKE :query", Product.class )
                .setParameter( "query", query )
                .getResultList();

        if ( products.isEmpty() )
            throw new NotFoundException();

        GenericEntity<List<Product>> genericEntity = new GenericEntity<>( products ) {
        };

        em.close();
        return Response.ok().entity( genericEntity ).build();
    }

    @DELETE
    @Path( "{id}" )
    public Response deleteProduct( @PathParam( "id" ) int id ) {
        pr.findOne( id ).ifPresentOrElse( pr::delete, () -> {
            throw new NotFoundException();
        } );
        tx.commit();
        em.close();
        return Response.noContent().build();
    }
}
