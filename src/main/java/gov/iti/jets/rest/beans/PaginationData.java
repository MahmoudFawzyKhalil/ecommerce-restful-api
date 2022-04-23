package gov.iti.jets.rest.beans;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PaginationData {
    @DefaultValue( "0" )
    @QueryParam( "offset" )
    private int offset;

    @DefaultValue( "10" )
    @QueryParam( "limit" )
    private int limit;

    public PaginationData() {
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit( int limit ) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset( int offset ) {
        this.offset = offset;
    }


    @Override
    public String toString() {
        return "PaginationData{" +
                "offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
