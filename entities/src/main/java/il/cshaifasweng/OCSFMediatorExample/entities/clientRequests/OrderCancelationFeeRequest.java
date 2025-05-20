package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import il.cshaifasweng.OCSFMediatorExample.entities.models.OrderClient;
import java.io.Serializable;

public class OrderCancelationFeeRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final OrderClient order;

    public OrderCancelationFeeRequest(OrderClient order) {
        this.order = order;
    }

    public OrderClient getOrder() {
        return order;
    }
}
