package gov.iti.jets.persistence;

import gov.iti.jets.domain.models.User;
import gov.iti.jets.rest.beans.PaginationData;
import jakarta.persistence.EntityManager;

import java.util.List;

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
}
