package fcmb.com.good.model.dto.request.orderItemRequest;

import lombok.Data;

import java.util.Date;
@Data
public class OrderItemsRequest {
    private String itemName;
    private Double salesPrice;
    private Integer quantity;
    private Double amount;
    private Double purchasePrice;
    private Double purchaseAmount;

    private String serviceName;
    private String description;


}
