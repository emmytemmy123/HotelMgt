package fcmb.com.good.model.dto.response.transactionResponse;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderHelperResponse {

    private String itemName;
    private Integer quantity;
    private Double amount;
    private Double purchasePrice;
    private Double purchaseAmount;
    private String serviceName;
    private String description;
    private LocalDateTime transactionDate;
    private String status;


}
