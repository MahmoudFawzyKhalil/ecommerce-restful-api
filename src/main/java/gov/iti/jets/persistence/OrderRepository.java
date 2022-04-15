package gov.iti.jets.persistence;

import gov.iti.jets.domain.models.Order;
import gov.iti.jets.domain.models.User;
import jakarta.persistence.EntityManager;

public class OrderRepository extends AbstractRepository<Order> {
    public OrderRepository( EntityManager entityManager) {
        super(entityManager);
        this.setClazz(Order.class);
    }
}
