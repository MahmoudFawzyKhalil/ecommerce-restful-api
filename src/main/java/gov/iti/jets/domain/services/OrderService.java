package gov.iti.jets.domain.services;

import gov.iti.jets.domain.enums.OrderStatus;
import gov.iti.jets.domain.exceptions.BusinessException;
import gov.iti.jets.domain.models.Order;
import gov.iti.jets.domain.models.User;
import gov.iti.jets.persistence.JpaUtil;
import gov.iti.jets.persistence.OrderRepository;
import gov.iti.jets.persistence.UserRepository;

public class OrderService {
    public static Order updateOrderStatus( int orderId, OrderStatus newStatus ) {
        var em = JpaUtil.createEntityManager();

        try {
            var tx = em.getTransaction();
            var or = new OrderRepository( em );
            tx.begin();

            Order order = or.findOne( orderId ).orElseThrow( () -> new BusinessException( "No order exists with the id " + orderId, 400 ) );

            order.setStatus( newStatus );

            tx.commit();
            return order;
        } finally {
            em.close();
        }
    }
}
