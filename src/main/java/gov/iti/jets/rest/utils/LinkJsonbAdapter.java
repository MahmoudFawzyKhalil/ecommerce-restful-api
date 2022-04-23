package gov.iti.jets.rest.utils;

import gov.iti.jets.rest.beans.AdaptedLink;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.ws.rs.core.Link;

import java.util.List;
import java.util.stream.Collectors;

public class LinkJsonbAdapter implements JsonbAdapter<List<Link>, List<AdaptedLink>> {
    @Override
    public List<AdaptedLink> adaptToJson( List<Link> obj ) {
        return obj.stream()
                .map( link -> new AdaptedLink( link.getUri(), link.getRel() ) )
                .collect( Collectors.toList() );
    }

    @Override
    public List<Link> adaptFromJson( List<AdaptedLink> obj ) {
        return obj.stream()
                .map( adaptedLink -> Link.fromUri( adaptedLink.getUri() ).rel( adaptedLink.getRel() ).build() )
                .collect( Collectors.toList() );
    }
}
