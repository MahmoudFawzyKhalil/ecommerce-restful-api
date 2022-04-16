package gov.iti.jets.persistence;

import gov.iti.jets.api.resource.category.CategoryFilters;
import gov.iti.jets.api.resource.category.CategoryResponse;
import gov.iti.jets.api.beans.PaginationData;
import gov.iti.jets.domain.models.Category;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryRepository extends AbstractRepository<Category> {
    public CategoryRepository( EntityManager entityManager ) {
        super( entityManager );
        this.setClazz( Category.class );
    }

    public List<CategoryResponse> findCategoryResponses( PaginationData pagination, CategoryFilters filters ) {
        return entityManager.createQuery(
                        "SELECT new gov.iti.jets.api.resource.category.CategoryResponse (c.id, c.name) " +
                                "FROM Category c " +
                                "WHERE c.name LIKE :name " +
                                "ORDER BY c.id",
                        CategoryResponse.class )
                .setParameter( "name", String.format( "%%%s%%", filters.getName() ) )
                .setFirstResult( pagination.getOffset() )
                .setMaxResults( pagination.getLimit() )
                .getResultList();
    }

    public long getNumberOfCategories() {
        return entityManager.createQuery( "SELECT COUNT (c) FROM Category c",
                        Long.class )
                .getSingleResult();
    }
}
