package fcmb.com.good.utills.EndPoints;


public class AssetsEndPoints {

    public static final String assets="assets";

    public static final String ASSETS_CATEGORY="/category";
    public static final String FIND_ASSETS_CATEGORY=ASSETS_CATEGORY+"/list";
    public static final String ADD_ASSETS_CATEGORY=ASSETS_CATEGORY+"/add";
    public static final String FIND_ASSETS_CATEGORY_BY_ID=ASSETS_CATEGORY+"/{id}";
    public static final String UPDATE_ASSETS_CATEGORY= ASSETS_CATEGORY+"/update/{id}";
    public static final String DELETE_ASSETS_CATEGORY= ASSETS_CATEGORY+"/delete/{id}";

    public static final String ASSET="";
    public static final String FIND_ASSET=ASSET+"/list";
    public static final String ADD_ASSET=ASSET+"/add";
    public static final String FIND_ASSET_BY_ID=ASSET+"/{id}";
    public static final String UPDATE_ASSET= ASSET+"/update/{id}";
    public static final String DELETE_ASSET= ASSET+"/delete/{id}";


    public static final String DAMAGED_ASSET="/damagedAssets";
    public static final String FIND_DAMAGED_ASSET=DAMAGED_ASSET+"/list";
    public static final String ADD_DAMAGED_ASSET=DAMAGED_ASSET+"/add";
    public static final String FIND_DAMAGED_ASSET_BY_ID=DAMAGED_ASSET+"/{id}";
    public static final String UPDATE_DAMAGED_ASSET= DAMAGED_ASSET+"/update/{id}";
    public static final String DELETE_DAMAGED_ASSET= DAMAGED_ASSET+"/delete/{id}";
    public static final String FIND_DAMAGED_ASSETS_BY_ROOM_NUMBER_AND_CATEGORY = DAMAGED_ASSET+"/searchDamagedAssetsListByRoomNumberAndCategory";
    public static final String FIND_DAMAGED_ASSETS_BY_NAME = DAMAGED_ASSET+"/searchDamagedAssetsListByName";





}
