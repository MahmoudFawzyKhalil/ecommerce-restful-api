package gov.iti.jets.rest.beans;

import java.net.URI;

public class AdaptedLink {

    private URI uri;
    private String rel;

    public AdaptedLink( URI uri, String rel ) {
        this.uri = uri;
        this.rel = rel;
    }

    public AdaptedLink() {
    }

    public URI getUri() {
        return uri;
    }

    public void setUri( URI uri ) {
        this.uri = uri;
    }

    public String getRel() {
        return rel;
    }

    public void setRel( String rel ) {
        this.rel = rel;
    }

    @Override
    public String toString() {
        return "AdaptedLink [rel=" + rel + ", uri=" + uri + "]";
    }

}
