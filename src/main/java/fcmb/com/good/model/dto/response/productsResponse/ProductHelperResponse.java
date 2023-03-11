package fcmb.com.good.model.dto.response.productsResponse;


import lombok.Data;

@Data
public class ProductHelperResponse {

    private String name;
    private String fileName;
    private String description;
    private Integer quantity;
    private String status;


}
