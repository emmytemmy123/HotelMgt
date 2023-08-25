package fcmb.com.good.utills.EndPoints;


public class AssetsEndPoints {

    public static final String events="events";

    public static final String EVENTS_CATEGORY="/category";
    public static final String FIND_EVENTS_CATEGORY=EVENTS_CATEGORY+"/list";
    public static final String ADD_EVENTS_CATEGORY=EVENTS_CATEGORY+"/add";
    public static final String FIND_EVENTS_CATEGORY_BY_ID=EVENTS_CATEGORY+"/{id}";
    public static final String UPDATE_EVENTS_CATEGORY= EVENTS_CATEGORY+"/update/{id}";
    public static final String DELETE_EVENTS_CATEGORY= EVENTS_CATEGORY+"/delete/{id}";

    public static final String EVENTS="";
    public static final String FIND_EVENTS=EVENTS+"/list";
    public static final String ADD_EVENTS=EVENTS+"/add";
    public static final String FIND_EVENTS_BY_ID=EVENTS+"/{id}";
    public static final String UPDATE_EVENTS= EVENTS+"/update/{id}";
    public static final String DELETE_EVENTS= EVENTS+"/delete/{id}";


    public static final String EVENT_ORDER="/order";
    public static final String FIND_EVENT_ORDER=EVENT_ORDER+"/list";
    public static final String ADD_EVENT_ORDER=EVENT_ORDER+"/add";
    public static final String FIND_EVENT_ORDER_BY_ID=EVENT_ORDER+"/{id}";
    public static final String UPDATE_EVENT_ORDER= EVENT_ORDER+"/update/{id}";
    public static final String DELETE_EVENT_ORDER= EVENT_ORDER+"/delete/{id}";


    public static final String DAMAGED_ASSET="/damagedAssets";
    public static final String FIND_DAMAGED_ASSET=DAMAGED_ASSET+"/list";
    public static final String ADD_DAMAGED_ASSET=DAMAGED_ASSET+"/add";
    public static final String FIND_DAMAGED_ASSET_BY_ID=DAMAGED_ASSET+"/{id}";
    public static final String UPDATE_DAMAGED_ASSET= DAMAGED_ASSET+"/update/{id}";
    public static final String DELETE_DAMAGED_ASSET= DAMAGED_ASSET+"/delete/{id}";
    public static final String FIND_DAMAGED_ASSETS_BY_ROOM_NUMBER_AND_CATEGORY = DAMAGED_ASSET+"/searchDamagedAssetsListByRoomNumberAndCategory";
    public static final String FIND_DAMAGED_ASSETS_BY_NAME = DAMAGED_ASSET+"/searchDamagedAssetsListByName";





}
