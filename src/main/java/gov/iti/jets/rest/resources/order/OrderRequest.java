package gov.iti.jets.rest.resources.order;

import gov.iti.jets.domain.enums.OrderStatus;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "order" )
public class OrderRequest {
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus( OrderStatus orderStatus ) {
        this.orderStatus = orderStatus;
    }
}
