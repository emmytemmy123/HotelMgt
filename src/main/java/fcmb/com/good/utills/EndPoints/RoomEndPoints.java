package fcmb.com.good.utills.EndPoints;



public class RoomEndPoints {

    public static final String USERS="USERS";

    public static final String ROOM="/room";
    public static final String FIND_ROOM=ROOM+"/list";
    public static final String ADD_ROOM=ROOM+"/add";
    public static final String FIND_ROOM_BY_ID=ROOM+"/{id}";
    public static final String UPDATE_ROOM= ROOM+"/update/{id}";
    public static final String DELETE_ROOM= ROOM+"/delete/{id}";

    public static final String ROOM_TYPE="/roomType";
    public static final String FIND_ROOM_TYPE=ROOM_TYPE+"/list";
    public static final String ADD_ROOM_TYPE= ROOM_TYPE+"/add";
    public static final String FIND_ROOM_TYPE_BY_ID= ROOM_TYPE+"/{id}";
    public static final String UPDATE_ROOM_TYPE= ROOM_TYPE+"/update/{id}";
    public static final String DELETE_ROOM_TYPE= ROOM_TYPE+"/delete/{id}";
    public static final String SEARCH_SUB_SERVICE_BY_ROOM_ID= ROOM_TYPE+"/searchSub_serviceListByRoomId";





}
