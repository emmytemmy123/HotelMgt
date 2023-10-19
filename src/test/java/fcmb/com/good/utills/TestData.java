package fcmb.com.good.utills;


import fcmb.com.good.service.UsersServiceTest;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.userRequest.UsersRequest;
import fcmb.com.good.model.dto.response.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.UsersResponse;
import fcmb.com.good.model.entity.user.UserCategory;
import fcmb.com.good.repo.user.UsersRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@RequiredArgsConstructor
@Component
public class TestData {

//    private final UsersRepository userRepository;

    private final UsersServiceTest usersServiceTest;

    private final UsersRepository usersRepository;


    public static List<UsersRequest> addUser() {

        // Mock behavior of repositories
        UserCategory existingUserCategory = new UserCategory();
        existingUserCategory.setName("customer");

//        Users user = new Users();

        UsersRequest request = new UsersRequest();
        request.setName("John");
        request.setEmail("john@example.com");
        request.setAddress("N0 3 park lane ");
        request.setCountry("Nigeria");
        request.setCity("lagos");
        request.setGender("male");
        request.setPassword("adezy");
        request.setPhone("09056742366");
        request.setUsername("adezy");
        request.setDesignation("admin");
        request.setUserCategory("customer");

        return List.of(request);

    }


//    public static ApiResponse<List<UsersResponse>> getListOfUsers() {
//        Users user = new Users();
//
//        user.setId(2L);
//        user.setName("John");
//        user.setEmail("john@gmail.com");
//        user.setAddress("No 4 Adebola Street");
//        user.setUsername("johnny");
//        user.setPassword("joshua123");
//        return List.of(user);
//    }


    public static String getContentType() {
        return "application/json";
    }



    public static UsersRequest getUseRequest () {
        UsersRequest user = new UsersRequest();

        //user.setId(1L);
        user.setName("Emmanuel");
        user.setEmail("emmytemmy@gmail.com");
        user.setAddress("No 4 Adebola Street");
        user.setUsername("homemade");
        user.setPassword("jemmy");

        return user;
    }


    public static ApiResponse<List<UsersResponse>> getListOfUsers(int page, int size) {

        return new ApiResponse(AppStatus.SUCCESS.label, List.of(UsersResponse.builder()

                        .name("adeniyi")
                        .address("N0 3 ade street")
                        .city("amoyo")
                        .email("emmy@gmail.com")
                        .country("nigeria")
                        .gender("male")
                        .nin("084705823470582370sdffd")
                        .phone("0907647383736")
                        .username("emmy")

                .build()));

    }







}
