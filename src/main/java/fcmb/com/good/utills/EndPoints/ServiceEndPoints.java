package fcmb.com.good.utills.EndPoints;


public class ServiceEndPoints {

    public static final String service="service";

    public static final String SERVICE="";
    public static final String FIND_SERVICE=SERVICE+"/list";
    public static final String ADD_SERVICE=SERVICE+"/add";
    public static final String FIND_SERVICE_BY_ID=SERVICE+"/{id}";
    public static final String UPDATE_SERVICE= SERVICE+"/update/{id}";
    public static final String DELETE_SERVICE= SERVICE+"/delete/{id}";

    public static final String SUB_SERVICE="/subServices";
    public static final String FIND_SUB_SERVICE=SUB_SERVICE+"/list";
    public static final String ADD_SUB_SERVICE=SUB_SERVICE+"/add";
    public static final String FIND_SUB_SERVICE_BY_ID=SUB_SERVICE+"/{id}";
    public static final String UPDATE_SUB_SERVICE= SUB_SERVICE+"/update/{id}";
    public static final String DELETE_SUB_SERVICE= SUB_SERVICE+"/delete/{id}";
    public static final String SEARCH_SUB_SERVICE_BY_ROOM= SUB_SERVICE+"/searchSubServiceListByRoom";
    public static final String SEARCH_SUB_SERVICE_BY_NAME= SUB_SERVICE+"/searchSubServiceListByName";
    public static final String SEARCH_SUB_SERVICE_BY_CUSTOMER_AND_ROOM= SUB_SERVICE+"/searchSubServiceListByCustomerAndRoom";



    public static final String SERVICE_REQUEST="/request";
    public static final String FIND_SERVICE_REQUEST=SERVICE_REQUEST+"/list";
    public static final String ADD_SERVICE_REQUEST= SERVICE_REQUEST+"/add";
    public static final String FIND_SERVICE_REQUEST_BY_ID= SERVICE_REQUEST+"/{id}";
    public static final String UPDATE_SERVICE_REQUEST= SERVICE_REQUEST+"/update/{id}";
    public static final String DELETE_SERVICE_REQUEST= SERVICE_REQUEST+"/delete/{id}";
    public static final String SEARCH_SERVICE_REQUEST_BY_NAME= SERVICE_REQUEST+"/searchServiceRequestListByName";





}
