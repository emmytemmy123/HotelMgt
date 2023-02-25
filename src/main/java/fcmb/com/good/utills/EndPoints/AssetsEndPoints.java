package fcmb.com.good.utills.EndPoints;


public class AssetsEndPoints {

    public static final String USERS="USERS";

    public static final String ASSETS_CATEGORY="/assetsCategory";
    public static final String FIND_ASSETS_CATEGORY=ASSETS_CATEGORY+"/list";
    public static final String ADD_ASSETS_CATEGORY=ASSETS_CATEGORY+"/add";
    public static final String FIND_ASSETS_CATEGORY_BY_ID=ASSETS_CATEGORY+"/{id}";
    public static final String UPDATE_ASSETS_CATEGORY= ASSETS_CATEGORY+"/update/{id}";
    public static final String DELETE_ASSETS_CATEGORY= ASSETS_CATEGORY+"/customerDelete/{id}";

    public static final String ASSET="/assets";
    public static final String FIND_ASSET=ASSET+"/list";
    public static final String ADD_ASSET=ASSET+"/add";
    public static final String FIND_ASSET_BY_ID=ASSET+"/{id}";
    public static final String UPDATE_ASSET= ASSET+"/update/{id}";
    public static final String DELETE_ASSET= ASSET+"/customerDelete/{id}";


    public static final String DAMAGED_ASSET="/damagedAssets";
    public static final String FIND_DAMAGED_ASSET=DAMAGED_ASSET+"/list";
    public static final String ADD_DAMAGED_ASSET=DAMAGED_ASSET+"/add";
    public static final String FIND_DAMAGED_ASSET_BY_ID=DAMAGED_ASSET+"/{id}";
    public static final String UPDATE_DAMAGED_ASSET= DAMAGED_ASSET+"/update/{id}";
    public static final String DELETE_DAMAGED_ASSET= DAMAGED_ASSET+"/customerDelete/{id}";
    public static final String FIND_DAMAGED_ASSETS_BY_ROOM_NUMBER_AND_CATEGORY = DAMAGED_ASSET+"/searchDamagedAssetsListByRoomNumberAndCategory";
    public static final String FIND_DAMAGED_ASSETS_BY_NAME = DAMAGED_ASSET+"/searchDamagedAssetsListByName";





}
