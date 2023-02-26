package fcmb.com.good;


import com.fasterxml.jackson.databind.ObjectMapper;
import fcmb.com.good.controller.usersControllers.UsersController;
import fcmb.com.good.services.user.UserServiceTest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
@RequiredArgsConstructor
@WebMvcTest(UsersController.class)
public class userControllerTest {

    private final UsersController userController;

    private final MockMvc mockMvc;

    private final UserServiceTest userServiceTest;

    private final ObjectMapper objectMapper;

 /* @Test
    public void getUserByIdTest() throws Exception {
        when(userServiceTest.getUserById(anyInt())).thenReturn(TestData.getListOfUsers().get(anyInt()));
         mockMvc.perform(MockMvcRequestBuilders.get("/user/2"))
                 .andDo(print())
                 .andExpect(status().isOk());
    }*/
//
//   @Test
//    public void saveUserTest() throws Exception {
//
//        User user = new User();
//        //user.setId(4);
//        user.setName("John");
//        user.setEmail("john@gmail.com");
//        user.setAddress("No 4 Adebola Street");
//        user.setUsername("johnny");
//        user.setPassword("joshua123");
//
//        when(userServiceTest.saveUser(any(User.class))).thenReturn((user));
//        mockMvc.perform(MockMvcRequestBuilders.post("/addUser")
//                .content(objectMapper.writeValueAsString(user))
//                .contentType(TestData.getContentType()))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
//
//    }




   /* @Test
    public void saveUserTest2() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn((User) TestData.addUser());
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .content(objectMapper.writeValueAsString(TestData.getUseRequest()))
                        .contentType(TestData.getContentType()))
                        .andExpect(status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                        .andDo(print());

    }
*/


    private ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


}
