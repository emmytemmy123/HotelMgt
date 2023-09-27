package fcmb.com.good;

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
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
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


//    @Before
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
//    }


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAddUsers() throws Exception {
        // Prepare a UsersRequest object with test data
        UsersRequest request = new UsersRequest();
        request.setName("John Doe");
        request.setEmail("johndoe@example.com");
        // Set other request attributes as needed...

        // Mock the behavior of repositories and encoder
        UserCategory existingUserCategory = new UserCategory();
        existingUserCategory.setName("UserCategoryName");

        when(usersCategoryRepository.findByName(request.getUserCategory()))
                .thenReturn(java.util.Optional.of(existingUserCategory));
        when(usersRepository.save(any(Users.class)))
                .thenReturn(new Users());
        when(activityLogRepository.save(any(ActivityLog.class)))
                .thenReturn(new ActivityLog());

        // Perform a POST request to your controller's endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.message").value("Record Added successfully"))
                .andExpect((ResultMatcher) jsonPath("$.status").value("SUCCESS"))
                .andExpect((ResultMatcher) jsonPath("$.statusCode").value(200));
    }


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
    public void returnListOfUsers() throws Exception {
        // Arrange
        int page = 1;
        int size = 10;
        List<Users> userList = Collections.singletonList(new Users(/* user data */));
        ApiResponse<List<UsersResponse>> expectedResponse = new ApiResponse<>(SUCCESS.label,
                Mapper.convertList(userList, UsersResponse.class)
        );

        // Mock the userService behavior
        when(userService.getListOfUsers(page, size)).thenReturn(expectedResponse);

        // Act: Call the controller method
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/list")
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size)))
                .andDo(print())
//              .andExpect(status().isOk())
                .andReturn();

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


    @Test
    void test_that_list_of_users_success() throws Exception {
        when(userService.getListOfUsers(anyInt(), anyInt()))
                .thenReturn(TestData.getListOfUsers(anyInt(), anyList().size()));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/list"))
                .andDo(print());

//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value(SUCCESS))
//                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
//                .andExpect(jsonPath("$.meta.totalElement").value(1))
//                .andExpect(jsonPath("$.meta.totalPage").value(1))
//                .andExpect(jsonPath("$.data").isArray());
        

    }









}
