package fcmb.com.good.utills.EndPoints;



public class ProductEndPoints {

    public static final String product="product";


    public static final String PRODUCT_PURCHASE="/purchase";
    public static final String FIND_PRODUCT_PURCHASE=PRODUCT_PURCHASE+"/list";
    public static final String ADD_PRODUCT_PURCHASE= PRODUCT_PURCHASE+"/add";
    public static final String FIND_PRODUCT_PURCHASE_BY_ID= PRODUCT_PURCHASE+"/{id}";
    public static final String UPDATE_PRODUCT_PURCHASE= PRODUCT_PURCHASE+"/update/{id}";
    public static final String DELETE_PRODUCT_PURCHASE= PRODUCT_PURCHASE+"/delete/{id}";
    public static final String SEARCH_PRODUCT_PURCHASE_BY_NAME= PRODUCT_PURCHASE+"/searchProductPurchaseListByName";
    public static final String SEARCH_PRODUCT_PURCHASE_BY_DATE_RANGE= PRODUCT_PURCHASE+"/searchProductPurchaseListByDateRange";


    public static final String PRODUCT_FACILITY="/facility";
    public static final String FIND_PRODUCT_FACILITY=PRODUCT_FACILITY+"/list";
    public static final String ADD_PRODUCT_FACILITY= PRODUCT_FACILITY+"/add";
    public static final String FIND_PRODUCT_FACILITY_BY_ID= PRODUCT_FACILITY+"/{id}";
    public static final String UPDATE_PRODUCT_FACILITY= PRODUCT_FACILITY+"/update/{id}";
    public static final String DELETE_PRODUCT_FACILITY= PRODUCT_FACILITY+"/delete/{id}";
    public static final String FIND_PRODUCT_FACILITY_BY_PRODUCT_UUID= PRODUCT_FACILITY+"/searchProductFacilityByProductId";
    public static final String SEARCH_PRODUCT_FACILITY_BY_DATE_RANGE= PRODUCT_FACILITY+"/searchProductPurchaseListByDateRange";



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
