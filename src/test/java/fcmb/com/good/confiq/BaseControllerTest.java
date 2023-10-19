package fcmb.com.good.confiq;

import com.fasterxml.jackson.databind.ObjectMapper;
import fcmb.com.good.controller.usersControllers.UsersController;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.user.UsersCategoryRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.services.user.UsersService;
import fcmb.com.good.services.user.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

public abstract class BaseControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();


    @MockBean
    public JpaProperties jpaProperties;
    @MockBean
    public DataSource dataSource;
    @MockBean
    private UsersController usersController;
    @MockBean
    private UsersRepository usersRepository;
    @MockBean
    private UsersCategoryRepository usersCategoryRepository;
    @MockBean
    private ActivityLogRepository activityLogRepository;
    @MockBean
    private UsersService userService;
    @MockBean
    private UsersServiceImpl usersServiceImpl;




}
