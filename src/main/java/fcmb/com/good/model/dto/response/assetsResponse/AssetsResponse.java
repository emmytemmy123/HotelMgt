package fcmb.com.good.model.dto.response.assetsResponse;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class AssetsResponse extends BaseDto {

    private String category_id;
    private String asset_name;
    private Long price;
    private String description;
    private Long record_id;
    private String record_type;
    private String code;
    private String Location;

}
