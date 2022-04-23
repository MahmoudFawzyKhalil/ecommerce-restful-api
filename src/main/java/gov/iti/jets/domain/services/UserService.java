package gov.iti.jets.domain.services;

import gov.iti.jets.domain.exceptions.BusinessException;
import gov.iti.jets.domain.models.User;
import gov.iti.jets.persistence.JpaUtil;
import gov.iti.jets.persistence.ProductRepository;
import gov.iti.jets.persistence.UserRepository;
import gov.iti.jets.rest.beans.PaginationData;

import java.util.List;
import java.util.Optional;

public class UserService {
    public static List<User> findUsers( PaginationData paginationData ) {
        var em = JpaUtil.createEntityManager();

        try {
            var ur = new UserRepository( em );
            return ur.findUsers( paginationData );
        } finally {
            em.close();
        }
    }

    public static long getNumberOfUsers() {
        var em = JpaUtil.createEntityManager();

        try {
            var ur = new UserRepository( em );
            return ur.getNumberOfUsers();
        } finally {
            em.close();
        }
    }

    public static void createUser( User user ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();
        tx.begin();

        try {
            var ur = new UserRepository( em );
            ur.create( user );
            tx.commit();
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            tx.rollback();
            throw new BusinessException( String.format( "Failed to create user: %s. " +
                    "Please try again later.", user.getEmail() ), e, 500 );
        } finally {
            em.close();
        }
    }

    public static Optional<User> findUserById( int id ) {
        var em = JpaUtil.createEntityManager();

        try {
            var ur = new UserRepository( em );
            return ur.findOne( id );
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            throw new BusinessException( String.format( "Failed to find user with id: %s. " +
                    "Please try again later.", id ), e, 500 );
        } finally {
            em.close();
        }
    }
}
