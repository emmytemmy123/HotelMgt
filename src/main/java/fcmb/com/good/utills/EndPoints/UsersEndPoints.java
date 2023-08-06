    package fcmb.com.good.utills.EndPoints;



public class UsersEndPoints {

    public static final String users="users";

    public static final String CUSTOMER="/customer";
    public static final String FIND_CUSTOMER=CUSTOMER+"/list";
    public static final String ADD_CUSTOMER=CUSTOMER+"/add";
    public static final String FIND_CUSTOMER_BY_ID=CUSTOMER+"/{uuid}";
    public static final String UPDATE_CUSTOMER= CUSTOMER+"/update/{uuid}";
    public static final String DELETE_CUSTOMER= CUSTOMER+"/customerDelete/{uuid}";
    public static final String LOGIN_CUSTOMER= CUSTOMER+"loginCustomer";
    public static final String RESET_PASSWORD= CUSTOMER+"/resetPassword";
    public static final String FORGOT_CUSTOMER_PASSWORD= CUSTOMER+"/forgotCustomerPassword";


    public static final String EMPLOYEE="/employee";
    public static final String FIND_EMPLOYEE=EMPLOYEE+"/list";
    public static final String ADD_EMPLOYEE= EMPLOYEE+"/add";
    public static final String FIND_EMPLOYEE_BY_ID= EMPLOYEE+"/{uuid}";
    public static final String UPDATE_EMPLOYEE= EMPLOYEE+"/update/{uuid}";
    public static final String DELETE_EMPLOYEE= EMPLOYEE+"/delete/{uuid}";
    public static final String LOGIN_EMPLOYEE= EMPLOYEE+"loginEmployee";
    public static final String RESET_EMPLOYEE_PASSWORD= EMPLOYEE+"/resetPassword";
    public static final String FORGOT_EMPLOYEE_PASSWORD= EMPLOYEE+"/forgotEmployeePassword";


    public static final String EMPLOYEE_SHIFT="/employeeShift";
    public static final String FIND_EMPLOYEE_SHIFT=EMPLOYEE_SHIFT+"/list";
    public static final String ADD_EMPLOYEE_SHIFT= EMPLOYEE_SHIFT+"/add";
    public static final String FIND_EMPLOYEE_SHIFT_BY_ID= EMPLOYEE_SHIFT+"/{uuid}";
    public static final String UPDATE_EMPLOYEE_SHIFT= EMPLOYEE_SHIFT+"/update/{uuid}";
    public static final String DELETE_EMPLOYEE_SHIFT= EMPLOYEE_SHIFT+"/delete/{uuid}";

    public static final String USER="";
    public static final String FIND_USER=USER+"/list";
    public static final String ADD_USER= USER+"/add";
    public static final String FIND_USER_BY_ID= USER+"/{uuid}";
    public static final String UPDATE_USER= USER+"/update/{uuid}";
    public static final String DELETE_USER= USER+"/delete/{uuid}";
    public static final String RESET_USER_PASSWORD= USER+"/resetPassword";
    public static final String FORGOT_USER_PASSWORD= USER+"/forgotUserPassword";
    public static final String LOGIN_USER= USER+"/loginUser";
    public static final String AUTHENTICATE_USER= USER+"authenticate";
    public static final String AUTHENTICATE_USERS= USER+"authenticate1";
    public static final String GIVE_ACCESS_TO_USER= USER+"/access/{uuid}/{userRole}";




    public static final String ROLE="/role";
    public static final String FIND_ROLE=ROLE+"/list";
    public static final String ADD_ROLE= ROLE+"/add";
    public static final String FIND_ROLE_BY_ID= ROLE+"/{uuid}";
    public static final String UPDATE_ROLE= ROLE+"/update/{uuid}";
    public static final String DELETE_ROLE= ROLE+"/delete/{uuid}";

    public static final String USERTYPE="/type";
    public static final String FIND_USERTYPE=USERTYPE+"/list";
    public static final String ADD_USERTYPE= USERTYPE+"/add";
    public static final String FIND_USERTYPE_BY_ID= USERTYPE+"/{uuid}";
    public static final String UPDATE_USERTYPE= USERTYPE+"/update/{uuid}";
    public static final String DELETE_USERTYPE= USERTYPE+"/delete/{uuid}";

    public static final String IMAGE="/image";
    public static final String FIND_IMAGE=IMAGE+"/list";
    public static final String ADD_IMAGE= IMAGE+"/add";
    public static final String FIND_IMAGE_BY_ID= IMAGE+"/{uuid}";
    public static final String UPDATE_IMAGE= IMAGE+"/update/{uuid}";
    public static final String DELETE_IMAGE= IMAGE+"/delete/{uuid}";






}
