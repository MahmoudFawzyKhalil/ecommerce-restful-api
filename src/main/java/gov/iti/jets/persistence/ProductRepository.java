package gov.iti.jets.persistence;

import gov.iti.jets.domain.models.Product;
import gov.iti.jets.rest.beans.PaginationData;
import gov.iti.jets.rest.resources.product.ProductFilters;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductRepository extends AbstractRepository<Product> {
    public ProductRepository( EntityManager entityManager ) {
        super( entityManager );
        this.setClazz( Product.class );
    }

    public List<Product> findProducts( PaginationData paginationData, ProductFilters filters ) {
        return entityManager.createQuery(
                        "SELECT p " +
                                "FROM Product p " +
                                "WHERE p.name LIKE :name " +
                                "ORDER BY p.id",
                        Product.class )
                .setParameter( "name", String.format( "%%%s%%", filters.getName() ) )
                .setFirstResult( paginationData.getOffset() )
                .setMaxResults( paginationData.getLimit() )
                .getResultList();
    }

    public long getNumberOfProducts() {
        return entityManager.createQuery( "SELECT COUNT (p) FROM Product p",
                        Long.class )
                .getSingleResult();
    }

    public List<Product> findProductsForCategory( int categoryId ) {
        return entityManager.createQuery( "SELECT c.products FROM Category c WHERE c.id = :cid", Product.class )
                .setParameter( "cid", categoryId )
                .getResultList();
    }
}
