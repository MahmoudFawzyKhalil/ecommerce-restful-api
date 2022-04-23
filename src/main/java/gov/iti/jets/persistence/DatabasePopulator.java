package gov.iti.jets.persistence;

import gov.iti.jets.domain.enums.Role;
import gov.iti.jets.domain.models.*;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.util.List;

public class DatabasePopulator {
    public static void main( String[] args ) {
        var em = JpaUtil.createEntityManager();
        var tx = em.getTransaction();
        tx.begin();

        var guitars = new Category( "Guitars" );
        var pianos = new Category( "Pianos" );

        em.persist( guitars );
        em.persist( pianos );

        var gibsonES335 = new Product( "Gibson ES 335", "Bright red semi-hollow guitar.", 5, new BigDecimal( "3449.99" ) );
        gibsonES335.addCategoryToProduct( guitars );

        var fenderStrat = new Product( "Fender 1965 Stratocaster", "Twangy.", 10, new BigDecimal( "1799.99" ) );
        fenderStrat.addCategoryToProduct( guitars );

        em.persist( gibsonES335 );
        em.persist( fenderStrat );

        var mary = new User( "Mary", "F.", "mary@gmail.com", Role.CUSTOMER );
        var mahmoud = new User( "Mahmoud", "F.", "mahmoud@gmail.com", Role.CUSTOMER );

        em.persist( mary );
        em.persist( mahmoud );

        mahmoud.getCart().addLineItemToCart( new CartLineItem( gibsonES335, 2 ) );
        mary.getCart().addLineItemToCart( new CartLineItem( fenderStrat, 10 ) );

        Order order = new Order( mahmoud );
        mahmoud.getCart().empty();

        em.persist( order );

        tx.commit();
        em.close();
        JpaUtil.closeEntityManagerFactory();
    }

}
