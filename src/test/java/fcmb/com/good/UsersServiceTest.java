//package fcmb.com.good;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import fcmb.com.good.controller.usersControllers.UsersController;
//import fcmb.com.good.model.dto.request.userRequest.UsersRequest;
//import fcmb.com.good.model.entity.user.UserCategory;
//import fcmb.com.good.repo.activityLog.ActivityLogRepository;
//import fcmb.com.good.repo.user.UsersCategoryRepository;
//import fcmb.com.good.repo.user.UsersRepository;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UsersController.class)
//@RequiredArgsConstructor
//public class UsersServiceTest {
//
//    @InjectMocks
//    private UsersController usersController;
//
//    @Mock
//    private UsersRepository usersRepository;
//
//    @Mock
//    private UsersCategoryRepository usersCategoryRepository;
//
//    @Mock
//    private ActivityLogRepository activityLogRepository;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    public void testAddUsers_Success() throws Exception {
//        // Mock behavior of repositories
//        UserCategory existingUserCategory = new UserCategory();
//        existingUserCategory.setName("customer");
//
//        when(usersCategoryRepository.findByName(anyString()))
//                .thenReturn(Optional.of(existingUserCategory));
//
//        UsersRequest request = new UsersRequest();
//        request.setName("John");
//        request.setEmail("john@example.com");
//        request.setAddress("N0 3 park lane ");
//        request.setCountry("Nigeria");
//        request.setCity("lagos");
//        request.setGender("male");
//        request.setPassword("adezy");
//        request.setPhone("09056742366");
//        request.setUsername("adezy");
//        request.setUserCategory("customer");
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
//
//        mockMvc.perform(post("/users/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Record Added successfully"));
//    }
//}
