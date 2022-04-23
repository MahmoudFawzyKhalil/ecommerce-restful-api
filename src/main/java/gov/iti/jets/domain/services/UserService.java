package gov.iti.jets.domain.services;

import gov.iti.jets.domain.exceptions.BusinessException;
import gov.iti.jets.domain.models.Cart;
import gov.iti.jets.domain.models.CartLineItem;
import gov.iti.jets.domain.models.Order;
import gov.iti.jets.domain.models.User;
import gov.iti.jets.persistence.JpaUtil;
import gov.iti.jets.persistence.OrderRepository;
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

    public static void deleteUser( int id ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();

        try {
            tx.begin();
            var ur = new UserRepository( em );
            ur.deleteById( id );
            tx.commit();
        } finally {
            em.close();
        }
    }

    public static void updateUser( User updatedUser ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();

        try {
            tx.begin();
            var ur = new UserRepository( em );
            ur.findOne( updatedUser.getId() ).ifPresentOrElse( ( user ) -> {
                user.update( updatedUser );
                ur.update( user );
            }, () -> ur.update( updatedUser ) );
            tx.commit();
        } finally {
            em.close();
        }
    }


    public static Optional<Cart> findCartByUserId( int id ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();
        tx.begin();

        try {
            var ur = new UserRepository( em );
            var optionalCart = ur.findCartByUserId( id );
            tx.commit();
            return optionalCart;
        } finally {
            em.close();
        }
    }

    public static Cart addItemToUserCart( int userId, int productId, int quantity ) {
        var em = JpaUtil.createEntityManager();
        try {
            var tx = em.getTransaction();
            var ur = new UserRepository( em );
            var pr = new ProductRepository( em );
            tx.begin();

            var cart = ur.findCartByUserId( userId )
                    .orElseThrow( () -> new BusinessException( "No user exists with the id " + userId, 400 ) );
            var product = pr.findOne( productId )
                    .orElseThrow( () -> new BusinessException( "No product exists with the id" + productId, 400 ) );

            var cartLineItem = new CartLineItem( product, quantity );

            cart.addLineItemToCart( cartLineItem );

            tx.commit();

            return cart;
        } finally {
            em.close();
        }
    }

    public static void clearUserCart( int userId ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();
        tx.begin();

        try {
            var ur = new UserRepository( em );
            ur.findCartByUserId( userId ).ifPresentOrElse( Cart::empty, () -> {
                tx.rollback();
                throw new BusinessException( "No user exists with the id " + userId, 400 );
            } );
            tx.commit();
        } finally {
            em.close();
        }
    }

    public static List<Order> getOrdersByUserId( int userId ) {
        var em = JpaUtil.createEntityManager();
        try {
            var tx = em.getTransaction();
            var ur = new UserRepository( em );
            tx.begin();

            List<Order> orders = ur.findOrdersByUserId( userId );

            tx.commit();

            return orders;
        } finally {
            em.close();
        }
    }

    public static Order createOrderFromUserCart( int userId ) {
        var em = JpaUtil.createEntityManager();

        try {
            var tx = em.getTransaction();
            var ur = new UserRepository( em );
            var or = new OrderRepository( em );
            tx.begin();


            User user = ur.findOne( userId )
                    .orElseThrow( () -> new BusinessException( "No user exists with the id " + userId, 400 ) );


            if ( user.getCart().getLineItems().isEmpty() )
                throw new BusinessException( "Can't create an order from an empty shopping cart.", 400 );

            Order order = new Order( user );

            or.create( order );

            user.getCart().empty();

            tx.commit();
            return order;
        } finally {
            em.close();
        }
    }
}
