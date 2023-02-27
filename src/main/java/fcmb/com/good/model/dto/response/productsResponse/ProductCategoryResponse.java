package fcmb.com.good.model.dto.response.productsResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class ProductCategoryResponse extends BaseDto {

    private String room;
    private String items;
    private String description;
}
