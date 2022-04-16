package gov.iti.jets.domain.services;

import gov.iti.jets.api.resource.category.CategoryFilters;
import gov.iti.jets.api.resource.category.CategoryResponse;
import gov.iti.jets.api.beans.PaginationData;
import gov.iti.jets.domain.exceptions.BusinessException;
import gov.iti.jets.domain.models.Category;
import gov.iti.jets.persistence.CategoryRepository;
import gov.iti.jets.persistence.JpaUtil;

import java.util.List;

public class CategoryService {
    public static List<CategoryResponse> findCategoryResponses( PaginationData pagination, CategoryFilters filters ) {
        var em = JpaUtil.createEntityManager();

        try {
            var cr = new CategoryRepository( em );
            return cr.findCategoryResponses( pagination, filters );
        } finally {
            em.close();
        }
    }

    public static long getNumberOfCategories() {
        var em = JpaUtil.createEntityManager();

        try {
            var cr = new CategoryRepository( em );
            return cr.getNumberOfCategories();
        } finally {
            em.close();
        }
    }

    public static void createNewCategory( Category category ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();
        tx.begin();

        try {
            var cr = new CategoryRepository( em );
            cr.create( category );
            tx.commit();
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            tx.rollback();
            throw new BusinessException( String.format( "Failed to create category: %s. " +
                    "Please try again later.", category.getName() ), e, 500 );
        } finally {
            em.close();
        }
    }
}
