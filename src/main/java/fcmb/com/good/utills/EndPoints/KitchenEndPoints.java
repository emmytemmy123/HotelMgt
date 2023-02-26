package fcmb.com.good.utills.EndPoints;



public class KitchenEndPoints {

    public static final String kitchen="kitchen";

    public static final String KITCHEN="";
    public static final String FIND_KITCHEN=KITCHEN+"/list";
    public static final String ADD_KITCHEN= KITCHEN+"/add";
    public static final String FIND_KITCHEN_BY_ID= KITCHEN+"/{id}";
    public static final String UPDATE_KITCHEN= KITCHEN+"/update/{id}";
    public static final String DELETE_KITCHEN= KITCHEN+"/delete/{id}";
    public static final String SEARCH_KITCHEN_BY_NAME= KITCHEN+"/searchKitchenListByName";
    public static final String SEARCH_KITCHEN_BY_CATEGORY= KITCHEN+"/searchKitchenListByCategory";


    public static final String KITCHEN_CATEGORY="/category";
    public static final String FIND_KITCHEN_CATEGORY=KITCHEN_CATEGORY+"/list";
    public static final String ADD_KITCHEN_CATEGORY= KITCHEN_CATEGORY+"/add";
    public static final String FIND_KITCHEN_CATEGORY_BY_ID= KITCHEN_CATEGORY+"/{id}";
    public static final String UPDATE_KITCHEN_CATEGORY= KITCHEN_CATEGORY+"/update/{id}";
    public static final String DELETE_KITCHEN_CATEGORY= KITCHEN_CATEGORY+"/delete/{id}";
    public static final String SEARCH_KITCHEN_CATEGORY_BY_NAME= KITCHEN_CATEGORY+"/searchKitchenCategoryListByName";
    public static final String SEARCH_KITCHEN_CATEGORY_BY_CATEGORY= KITCHEN_CATEGORY+"/searchKitchenListByCategory";


    public static final String KITCHEN_ORDER="/order";
    public static final String FIND_KITCHEN_ORDER=KITCHEN_ORDER+"/list";
    public static final String ADD_KITCHEN_ORDER= KITCHEN_ORDER+"/add";
    public static final String FIND_KITCHEN_ORDER_BY_ID= KITCHEN_ORDER+"/{id}";
    public static final String UPDATE_KITCHEN_ORDER= KITCHEN_ORDER+"/update/{id}";
    public static final String DELETE_KITCHEN_ORDER= KITCHEN_ORDER+"/delete/{id}";
    public static final String SEARCH_KITCHEN_ORDER_BY_NAME= KITCHEN_ORDER+"/searchKitchenOrderList";



}
