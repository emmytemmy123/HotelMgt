package fcmb.com.good.model.dto.Others;


import fcmb.com.good.model.dto.request.userRequest.UserRequest;
import fcmb.com.good.model.entity.user.AppUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Data
@RequiredArgsConstructor
@Component
public class TestData {

//    private final UserRepository userRepository;


    public static List<AppUser> addUser() {
        
        List list = new ArrayList<>();

        AppUser user = new AppUser();

        user.setName("Adeniyi");
        user.setEmail("emmy@gmail.com");
        user.setAddress("No 4 Adebola Street");
        user.setUsername("emmytemmy123");
        user.setPassword("emmytemmy");

        user = new AppUser();
        user.setName("Emmanuel");
        user.setEmail("emmytemmy@gmail.com");
        user.setAddress("No 4 Adebola Street");
        user.setUsername("emmadex");
        user.setPassword("emmytemmy");

        user = new AppUser();
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setAddress("No 4 Adebola Street");
        user.setUsername("johnny");
        user.setPassword("joshua123");

        return List.of(user);

    }


    public static List<AppUser> getListOfUsers() {
        AppUser user = new AppUser();

        user.setId(2L);
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setAddress("No 4 Adebola Street");
        user.setUsername("johnny");
        user.setPassword("joshua123");
        return List.of(user);
    }


    public static String getContentType() {
        return "application/json";
    }



    public static UserRequest getUseRequest () {
        UserRequest user = new UserRequest();

        //user.setId(1L);
        user.setName("Emmanuel");
        user.setEmail("emmytemmy@gmail.com");
        user.setAddress("No 4 Adebola Street");
        user.setUsername("homemade");
        user.setPassword("jemmy");

        return user;
    }
}
