package gov.iti.jets.api.resource.category;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import gov.iti.jets.domain.models.Category;
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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.*;

@Path("categories")
@Consumes({ "application/json; qs=1", MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class CategoryResource {
    private final CategoryRepository cr;
    private final ProductRepository pr;
    private final EntityManager em;
    private final EntityTransaction tx;

    @Context
    private UriInfo uriInfo;

    {
        em = JpaUtil.createEntityManager();
        cr = new CategoryRepository(em);
        pr = new ProductRepository(em);
        tx = em.getTransaction();
        tx.begin();
    }

    @POST
    public Response createCategory(Category category) {
        cr.create(category);
        tx.commit();
        em.close();
        return Response.created(URI.create(uriInfo.getPath() + "/" + category.getId())).entity(category).build();
    }

    @GET
    public Response readAllCategories(@DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("5") @QueryParam("limit") int limit) {
        var categories = em.createQuery("SELECT c FROM Category c ORDER BY c.id", Category.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        if (categories.isEmpty())
            throw new NotFoundException();

        em.close();
        GenericEntity<List<Category>> entity = new GenericEntity<>(categories) {
        };
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    public Response readCategoryById(@PathParam("id") int id) {
        Category category = cr.findOne(id).orElseThrow(NotFoundException::new);
        CategoryResponse categoryResponse = new CategoryResponse(category);
        em.close();

        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()).rel("self").build();

        categoryResponse.setLinks( Arrays.asList(self));

        return Response.ok().entity(categoryResponse).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCategory(@PathParam("id") int id) {
        cr.findOne(id).ifPresentOrElse(cr::delete, () -> {
            throw new NotFoundException();
        });
        tx.commit();
        em.close();
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    public Response updateCategory(@PathParam("id") int id, Category updatedCategory) {
        updatedCategory.setId(id);
        cr.update(updatedCategory);
        tx.commit();
        em.close();
        return Response.ok().entity(updatedCategory).build();
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
