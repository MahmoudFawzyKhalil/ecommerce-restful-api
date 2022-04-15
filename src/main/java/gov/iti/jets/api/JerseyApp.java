package gov.iti.jets.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@ApplicationPath( "api" )
public class JerseyApp extends Application {
    @Override
    public Map<String, Object> getProperties() {
        return createApplicationPropertiesMap();
    }

    private Map<String, Object> createApplicationPropertiesMap() {
        return getApplicationPropertiesFromFile().entrySet().stream()
                .collect( Collectors.toMap( e -> (String) e.getKey(), Map.Entry::getValue ) );
    }

    private Properties getApplicationPropertiesFromFile() {
        try {
            Properties properties = new Properties();
            properties.load( getClass().getResourceAsStream( "/app.properties" ) );
            return properties;
        } catch ( IOException e ) {
            throw new RuntimeException( "Failed to load app.properties.", e );
        }
    }
}
