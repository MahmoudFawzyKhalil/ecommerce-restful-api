package gov.iti.jets.api;

import gov.iti.jets.domain.models.Product;
import gov.iti.jets.persistence.CategoryRepository;
import gov.iti.jets.persistence.JpaUtil;
import gov.iti.jets.persistence.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.*;

class ProductResourceTest {

    private ProductRepository pr;
    private CategoryRepository cr;
    private EntityManager em;
    private EntityTransaction tx;
    private WebTarget client;
    @Context
    private UriInfo uriInfo;

    @BeforeEach
    void setUp() {
        em = JpaUtil.createEntityManager();
        pr = new ProductRepository( em );
        cr = new CategoryRepository( em );
        tx = em.getTransaction();
        client = ClientBuilder.newClient().target( "http://localhost:8080/rest/api/products" );
    }

    @AfterEach
    void tearDown() {
        em.close();
    }

    @Test
    void createProductShouldFailIfCantCreateProduct() {
        Product product = new Product( "Test product", "test description", 0, BigDecimal.ONE );

        try {
            client.request( APPLICATION_JSON )
                    .post( Entity.entity( product, APPLICATION_JSON ), Product.class );
        } catch ( Exception e ) {
            fail();
        }
    }

    @Test
    void readProductByIdShouldFailIfReturnsWrongProduct() {
        Product product = client.path( "{id}" )
                .resolveTemplate( "id", 361 )
                .request( APPLICATION_JSON )
                .get( Product.class );
        pr.findOne( product.getId() ).ifPresent( ( p ) -> {
            if ( !p.equals( product ) ) {
                fail();
            }
        } );
    }

    @Test
    void readAllProductsShouldFailIfReturnsEmptyList() {
        try {
            GenericType<List<Product>> gt = new GenericType<List<Product>>() {
            };
            List<Product> products = client.request( APPLICATION_JSON ).get( gt );
            if ( products.isEmpty() ) {
                fail();
            }
        } catch ( Exception e ) {
            fail();
        }
    }
}