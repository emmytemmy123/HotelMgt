package fcmb.com.good.utills.EndPoints;



public class ProductEndPoints {

    public static final String product="product";

    public static final String PRODUCT_ORDER_ITEMS="/OrderItems";
    public static final String FIND_PRODUCT_ORDER_ITEMS=PRODUCT_ORDER_ITEMS+"/list";
    public static final String ADD_PRODUCT_ORDER_ITEMS=PRODUCT_ORDER_ITEMS+"/add";
    public static final String FIND_PRODUCT_ORDER_ITEMS_BY_ID=PRODUCT_ORDER_ITEMS+"/{id}";
    public static final String UPDATE_PRODUCT_ORDER_ITEMS= PRODUCT_ORDER_ITEMS+"/update/{id}";
    public static final String DELETE_PRODUCT_ORDER_ITEMS= PRODUCT_ORDER_ITEMS+"/customerDelete/{id}";

    public static final String PRODUCT_ORDER="/order";
    public static final String FIND_PRODUCT_ORDER=PRODUCT_ORDER+"/list";
    public static final String ADD_PRODUCT_ORDER= PRODUCT_ORDER+"/add";
    public static final String FIND_PRODUCT_ORDER_BY_ID= PRODUCT_ORDER+"/{id}";
    public static final String UPDATE_PRODUCT_ORDER= PRODUCT_ORDER+"/update/{id}";
    public static final String DELETE_PRODUCT_ORDER= PRODUCT_ORDER+"/delete/{id}";

    public static final String PRODUCT_PURCHASE="/purchase";
    public static final String FIND_PRODUCT_PURCHASE=PRODUCT_PURCHASE+"/list";
    public static final String ADD_PRODUCT_PURCHASE= PRODUCT_PURCHASE+"/add";
    public static final String FIND_PRODUCT_PURCHASE_BY_ID= PRODUCT_PURCHASE+"/{id}";
    public static final String UPDATE_PRODUCT_PURCHASE= PRODUCT_PURCHASE+"/update/{id}";
    public static final String DELETE_PRODUCT_PURCHASE= PRODUCT_PURCHASE+"/delete/{id}";
    public static final String SEARCH_PRODUCT_PURCHASE_BY_NAME= PRODUCT_PURCHASE+"/searchProductPurchaseListByName";
    public static final String SEARCH_PRODUCT_PURCHASE_BY_DATE_RANGE= PRODUCT_PURCHASE+"/searchProductPurchaseListByDateRange";



    public static final String PRODUCT="";
    public static final String FIND_PRODUCT=PRODUCT+"/list";
    public static final String ADD_PRODUCT= PRODUCT+"/add";
    public static final String FIND_PRODUCT_BY_ID= PRODUCT+"/{id}";
    public static final String UPDATE_PRODUCT= PRODUCT+"/update/{id}";
    public static final String DELETE_PRODUCT= PRODUCT+"/delete/{id}";
    public static final String SEARCH_PRODUCT_BY_CATEGORY= PRODUCT+"/searchProductListByCategory";
    public static final String SEARCH_PRODUCT_BY_NAME= PRODUCT+"/searchProductListByName";


    public static final String PRODUCT_CATEGORY="/category";
    public static final String FIND_PRODUCT_CATEGORY=PRODUCT_CATEGORY+"/list";
    public static final String ADD_PRODUCT_CATEGORY= PRODUCT_CATEGORY+"/add";
    public static final String FIND_PRODUCT_CATEGORY_BY_ID= PRODUCT_CATEGORY+"/{id}";
    public static final String UPDATE_PRODUCT_CATEGORY= PRODUCT_CATEGORY+"/update/{id}";
    public static final String DELETE_PRODUCT_CATEGORY= PRODUCT_CATEGORY+"/delete/{id}";


}
