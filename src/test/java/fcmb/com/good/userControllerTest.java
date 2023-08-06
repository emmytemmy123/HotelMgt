package fcmb.com.good;


import com.fasterxml.jackson.databind.ObjectMapper;
import fcmb.com.good.controller.usersControllers.UsersController;
import fcmb.com.good.model.dto.Others.TestData;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.services.user.UserServiceTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@RequiredArgsConstructor
@WebMvcTest(UsersController.class)
public class userControllerTest {

    private final MockMvc mockMvc;

    private final UserServiceTest userServiceTest;

    private final ObjectMapper objectMapper;



  @Test
    public void getUserByIdTest() throws Exception {
        when(userServiceTest.getUserById(anyInt())).thenReturn(TestData.getListOfUsers().get(anyInt()));
         mockMvc.perform(MockMvcRequestBuilders.get("/users/2"))
                 .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void saveUserTest() throws Exception {

        AppUser user = new AppUser();

        user.setId(4L);
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setAddress("No 4 ola Street");
        user.setUsername("johnny");
        user.setPassword("joshua123");

        when(userServiceTest.saveUser(any(AppUser.class))).thenReturn((user));
        mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
                .content(objectMapper.writeValueAsString(user))
                .contentType(TestData.getContentType()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }



    @Test
    public void saveUserTest2() throws Exception {

        when(userServiceTest.saveUser(any(AppUser.class))).thenReturn((AppUser) TestData.addUser());
        mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
                        .content(objectMapper.writeValueAsString(TestData.getUseRequest()))
                        .contentType(TestData.getContentType()))
                        .andExpect(status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                        .andDo(print());

    }



//    private ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }


}
