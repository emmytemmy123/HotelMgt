package fcmb.com.good.model.dto.request.userRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static fcmb.com.good.utills.MessageUtil.*;

@Data
public class EmployeeRequest  {

        @NotNull(message = INVALID_FIRSTNAME)
        @NotEmpty(message = INVALID_FIRSTNAME)
        String name;

        @NotNull(message = INVALID_EMAIL)
        @NotEmpty(message = INVALID_EMAIL)
        String email;

//        @NotNull(message = INVALID_GENDER)
//        @NotEmpty(message = INVALID_GENDER)
        String gender;

//        @NotNull(message = INVALID_COUNTRY)
//        @NotEmpty(message = INVALID_COUNTRY)
        String country;

        @NotNull(message = INVALID_CITY)
        @NotEmpty(message = INVALID_CITY)
        String city;

        @NotNull(message = INVALID_ADDRESS)
        @NotEmpty(message = INVALID_ADDRESS)
        String address;

        @NotNull(message = INVALID_PHONE)
        @NotEmpty(message = INVALID_PHONE)
        String phone;

        @NotNull(message = INVALID_PHONE)
        @NotEmpty(message = INVALID_PHONE)
        String photo;

        @NotNull(message = INVALID_DESIGNATION)
        @NotEmpty(message = INVALID_DESIGNATION)
        String designation;

        @NotNull(message = INVALID_USERNAME)
        @NotEmpty(message = INVALID_USERNAME)
        String username;

        @NotNull(message = INVALID_PASSWORD)
        @NotEmpty(message = INVALID_PASSWORD)
        String password;

        @NotNull(message = INVALID_USERNAME)
        @NotEmpty(message = INVALID_USERNAME)
        String postedBy;

        @NotNull(message = INVALID_USERNAME)
        @NotEmpty(message = INVALID_USERNAME)
        String role;

}
