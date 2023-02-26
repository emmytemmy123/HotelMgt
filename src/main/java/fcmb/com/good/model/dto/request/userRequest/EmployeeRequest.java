package fcmb.com.good.model.dto.request.userRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.*;

@Data
public class EmployeeRequest  {

        @NotNull(message = INVALID_FIRSTNAME)
        @NotEmpty(message = INVALID_FIRSTNAME)
        private String name;

        @NotNull(message = INVALID_EMAIL)
        @NotEmpty(message = INVALID_EMAIL)
        private String email;

//        @NotNull(message = INVALID_GENDER)
//        @NotEmpty(message = INVALID_GENDER)
        private String gender;

//        @NotNull(message = INVALID_COUNTRY)
//        @NotEmpty(message = INVALID_COUNTRY)
        private String country;

        @NotNull(message = INVALID_CITY)
        @NotEmpty(message = INVALID_CITY)
        private String city;

        @NotNull(message = INVALID_ADDRESS)
        @NotEmpty(message = INVALID_ADDRESS)
        private String address;

        @NotNull(message = INVALID_PHONE)
        @NotEmpty(message = INVALID_PHONE)
        private String phone;

        @NotNull(message = INVALID_PHONE)
        @NotEmpty(message = INVALID_PHONE)
        private String photo;

        @NotNull(message = INVALID_DESIGNATION)
        @NotEmpty(message = INVALID_DESIGNATION)
        private String designation;

        @NotNull(message = INVALID_USERNAME)
        @NotEmpty(message = INVALID_USERNAME)
        private String username;

        @NotNull(message = INVALID_PASSWORD)
        @NotEmpty(message = INVALID_PASSWORD)
        private String password;

        @NotNull(message = INVALID_USERNAME)
        @NotEmpty(message = INVALID_USERNAME)
        private String role;

        @NotNull(message = INVALID_USERNAME)
//        @NotEmpty(message = INVALID_USERNAME)
        private UUID createdById;

}
