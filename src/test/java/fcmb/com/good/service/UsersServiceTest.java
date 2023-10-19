package fcmb.com.good.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fcmb.com.good.controller.usersControllers.UsersController;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.request.userRequest.UsersRequest;
import fcmb.com.good.model.dto.response.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.UsersResponse;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.user.UserCategory;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.user.UsersCategoryRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.services.user.UsersService;
import fcmb.com.good.services.user.UsersServiceImpl;
import fcmb.com.good.utills.TestData;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fcmb.com.good.model.dto.enums.AppStatus.SUCCESS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@RequiredArgsConstructor
public class UsersServiceTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UsersCategoryRepository usersCategoryRepository;

    @Mock
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UsersService userService;

    @Mock
    private UsersServiceImpl usersServiceImpl;

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }


//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }


    @Test
    public void testAddUsers_Success() throws Exception {

        // Mock behavior of repositories
        UserCategory existingUserCategory = new UserCategory();
        existingUserCategory.setName("customer");

        when(usersCategoryRepository.findByName(anyString()))
                .thenReturn(Optional.of(existingUserCategory));

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

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andDo(print())
                        .andExpect(status().isOk());
//                        .andExpect(jsonPath("$.message").value(SUCCESS));

    }


    @Test
    public void list_of_users_success() throws Exception {

        int page = 1;
        int size = 10;

        // Define sample data that the userService will return
        ApiResponse<List<UsersResponse>> mockUserResponses = TestData.getListOfUsers(page, size);

        // Mock the behavior of userService.getListOfUsers
        when(userService.getListOfUsers(anyInt(), anyInt())).thenReturn(mockUserResponses);

        // Perform the GET request to your controller's endpoint
        mockMvc.perform(get("/users/list"))
                .andDo(print());

//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("SUCCESS"))
//                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
//                .andExpect(jsonPath("$.meta.totalElement").value(1))
//                .andExpect(jsonPath("$.meta.totalPage").value(1))
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data[0].id").value(1)) // Update with your expected id value
//                .andDo(result -> {
//                    // Print the response content for debugging
//                    System.out.println(result.getResponse().getContentAsString());
//                });

        // Verify that userService.getListOfUsers was called with the specified parameters
//        verify(userService, times(1)).getListOfUsers(1, 10);
    }


    @Test
    public void testGetUsersById() {
        UUID userUuid = UUID.randomUUID(); // Replace with a valid UUID

        // Create a mock Users object or actual Users, if needed
        Users mockUser = new Users(/* user data */);

        // Define mock behavior for usersRepository
        when(usersRepository.findByUuid(userUuid)).thenReturn(Optional.of(mockUser));

        // Call the controller method
        ApiResponse<UsersResponse> actualResponse = usersController.getUsersById(userUuid);

        // Debugging: Print actualResponse for inspection
        System.out.println("Actual Response: " + actualResponse);

    }


    @Test
    public void testUpdateUsers() {

        UUID userUuid = UUID.randomUUID(); // Replace with a valid UUID
        UsersRequest mockRequest = new UsersRequest(/* populate with test data */);

        // Create a mock Users object
        Users mockUser = Mockito.mock(Users.class);

        // Define mock behavior for usersRepository save method
        when(usersRepository.save(any(Users.class))).thenReturn(mockUser);

        // Call the controller method
        ApiResponse<String> actualResponse = usersController.updateUsers(userUuid, mockRequest);

        // Debugging: Print actualResponse for inspection
        System.out.println("Actual Response: " + actualResponse);
    }


    @Test
    public void testUpdateUsersPhoto_Success() throws Exception {
        // Create a sample user UUID and photo file
        UUID userUuid = UUID.randomUUID();
        MultipartFile photoFile = new MockMultipartFile("photo.jpg", "photo.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0]);

        // Mock behavior of userService
        when(userService.updateUsersPhoto(userUuid, photoFile))
                .thenReturn(new ApiResponse<>(SUCCESS.label, "Record Updated Successfully"));

        // Test the controller method
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{uuid}/updatePhoto", userUuid)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(photoFile.getBytes()))
                        .andDo(print());
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"SUCCESS\",\"data\":\"Record Updated Successfully\"}"));
    }


    @Test
    public void testDeleteUsers_Success() {
        // Create a user with a known UUID for testing
        UUID userId = UUID.randomUUID();
        Users userToDelete = new Users();
        userToDelete.setUuid(userId);

        // Mock behavior of validateUser to return the user to be deleted
        when(usersServiceImpl.validateUser(userId)).thenReturn(userToDelete);

        // Mock behavior of usersRepository for the delete method
        Mockito.doNothing().when(usersRepository).delete(userToDelete);

        // Mock behavior of activityLogRepository for saving the log
        Mockito.when(activityLogRepository.save(any(ActivityLog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Perform the delete operation
        ApiResponse<String> actualResponse = usersController.deleteUser(userId);

        System.out.println("Actual Response :" + actualResponse);

    }











}
