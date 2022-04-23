package gov.iti.jets.config;

import gov.iti.jets.persistence.JpaUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {

    public BootstrapListener() {
    }

    @Override
    public void contextInitialized( ServletContextEvent sce ) {
        try {
            Class.forName( "gov.iti.jets.persistence.JpaUtil" );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed( ServletContextEvent sce ) {
        JpaUtil.closeEntityManagerFactory();
    }
}