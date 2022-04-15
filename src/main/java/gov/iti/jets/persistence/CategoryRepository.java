package gov.iti.jets.persistence;

import gov.iti.jets.domain.models.Category;
import jakarta.persistence.EntityManager;

public class CategoryRepository extends AbstractRepository<Category> {
    public CategoryRepository(EntityManager entityManager) {
        super(entityManager);
        this.setClazz(Category.class);
    }
}
