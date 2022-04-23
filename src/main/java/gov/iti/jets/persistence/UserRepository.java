package gov.iti.jets.persistence;

import gov.iti.jets.domain.models.Cart;
import gov.iti.jets.domain.models.Order;
import gov.iti.jets.domain.models.User;
import gov.iti.jets.rest.beans.PaginationData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class UserRepository extends AbstractRepository<User> {
    public UserRepository( EntityManager entityManager ) {
        super( entityManager );
        this.setClazz( User.class );
    }

    public List<User> findUsers( PaginationData paginationData ) {
        return entityManager.createQuery(
                        "SELECT u " +
                                "FROM User u " +
                                "ORDER BY u.id",
                        User.class )
                .setFirstResult( paginationData.getOffset() )
                .setMaxResults( paginationData.getLimit() )
                .getResultList();
    }

    public long getNumberOfUsers() {
        return entityManager.createQuery( "SELECT COUNT (u) FROM User u",
                        Long.class )
                .getSingleResult();
    }

    public Optional<Cart> findCartByUserId( int id ) {
        try {
            return Optional.of( entityManager.createQuery( "SELECT u.cart FROM User u WHERE u.id = :id", Cart.class )
                    .setParameter( "id", id )
                    .getSingleResult() );
        } catch ( NoResultException e ) {
            return Optional.empty();
        }
    }

    public List<Order> findOrdersByUserId( int userId ) {
        return entityManager.createQuery( "SELECT u.orders FROM User u WHERE u.id = :id", Order.class )
                .setParameter( "id", userId )
                .getResultList();
    }
}
