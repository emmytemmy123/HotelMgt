package fcmb.com.good.model.dto.request.transactionRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class AccountCategoryRequest  {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String code;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String currency;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String accountType;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private UUID createdById;


}
