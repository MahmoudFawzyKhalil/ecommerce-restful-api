package gov.iti.jets.domain.services;

import gov.iti.jets.rest.resources.category.CategoryFilters;
import gov.iti.jets.rest.resources.category.CategoryResponse;
import gov.iti.jets.rest.beans.PaginationData;
import gov.iti.jets.domain.exceptions.BusinessException;
import gov.iti.jets.domain.models.Category;
import gov.iti.jets.persistence.CategoryRepository;
import gov.iti.jets.persistence.JpaUtil;

import java.util.List;
import java.util.Optional;

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

    public static void createCategory( Category category ) {
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

    public static Optional<Category> findCategoryById( int id ) {
        var em = JpaUtil.createEntityManager();

        try {
            var cr = new CategoryRepository( em );
            return cr.findOne( id );
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            throw new BusinessException( String.format( "Failed to find category with id: %s. " +
                    "Please try again later.", id ), e, 500 );
        } finally {
            em.close();
        }
    }

    public static void deleteCategory( int id ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();

        try {
            tx.begin();
            var cr = new CategoryRepository( em );
            cr.deleteById( id );
            tx.commit();
        } finally {
            em.close();
        }
    }

    public static void updateCategory( Category category ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();

        try {
            tx.begin();
            var cr = new CategoryRepository( em );
            cr.update( category );
            tx.commit();
        } finally {
            em.close();
        }
    }

    public static List<Category> getAllCategories() {
        var em = JpaUtil.createEntityManager();

        try {
            var cr = new CategoryRepository( em );
            return cr.findAll();
        } finally {
            em.close();
        }
    }
}
