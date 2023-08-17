package fcmb.com.good.model.dto.Others;


import fcmb.com.good.model.dto.request.userRequest.UsersRequest;
import fcmb.com.good.model.entity.user.Users;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

import static fcmb.com.good.utills.EndPoints.UsersEndPoints.users;

@Data
@RequiredArgsConstructor
@Component
public class TestData {

//    private final UsersRepository userRepository;


    public static List<Users> addUser() {
        
        List list = new ArrayList<>();

        Users user = new Users();

        user.setName("Adeniyi");
        user.setEmail("emmy@gmail.com");
        user.setAddress("No 4 Adebola Street");
        user.setUsername("emmytemmy123");
        user.setPassword("emmytemmy");

        user = new Users();
        user.setName("Emmanuel");
        user.setEmail("emmytemmy@gmail.com");
        user.setAddress("No 4 Adebola Street");
        user.setUsername("emmadex");
        user.setPassword("emmytemmy");

        user = new Users();
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setAddress("No 4 Adebola Street");
        user.setUsername("johnny");
        user.setPassword("joshua123");

        return List.of(user);

    }


    public static List<Users> getListOfUsers() {
        Users user = new Users();

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
}
