package gov.iti.jets.domain.services;

import gov.iti.jets.domain.exceptions.BusinessException;
import gov.iti.jets.domain.models.Product;
import gov.iti.jets.persistence.CategoryRepository;
import gov.iti.jets.persistence.JpaUtil;
import gov.iti.jets.persistence.ProductRepository;
import gov.iti.jets.rest.beans.PaginationData;
import gov.iti.jets.rest.resources.product.ProductFilters;

import java.util.List;
import java.util.Optional;

public class ProductService {

    public static List<Product> findProducts( PaginationData paginationData, ProductFilters filters ) {
        var em = JpaUtil.createEntityManager();

        try {
            var pr = new ProductRepository( em );
            return pr.findProducts( paginationData, filters );
        } finally {
            em.close();
        }
    }

    public static long getNumberOfCategories() {
        var em = JpaUtil.createEntityManager();

        try {
            var pr = new ProductRepository( em );
            return pr.getNumberOfCategories();
        } finally {
            em.close();
        }
    }

    public static void createProduct( Product product ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();
        tx.begin();

        try {
            var pr = new ProductRepository( em );
            pr.create( product );
            tx.commit();
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            tx.rollback();
            throw new BusinessException( String.format( "Failed to create product: %s. " +
                    "Please try again later.", product.getName() ), e, 500 );
        } finally {
            em.close();
        }
    }

    public static Optional<Product> findProductById( int id ) {
        var em = JpaUtil.createEntityManager();

        try {
            var pr = new ProductRepository( em );
            return pr.findOne( id );
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            throw new BusinessException( String.format( "Failed to find category with id: %s. " +
                    "Please try again later.", id ), e, 500 );
        } finally {
            em.close();
        }
    }

    public static void deleteProduct( int id ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();

        try {
            tx.begin();
            var pr = new ProductRepository( em );
            pr.deleteById( id );
            tx.commit();
        } finally {
            em.close();
        }
    }

    public static void updateProduct( Product updatedProduct ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();

        try {
            tx.begin();
            var pr = new ProductRepository( em );
            pr.findOne( updatedProduct.getId() ).ifPresentOrElse( ( p ) -> {
                updatedProduct.setCategories( p.getCategories() );
                pr.update( updatedProduct );
            }, () -> pr.update( updatedProduct ) );
            tx.commit();
        } finally {
            em.close();
        }
    }
}