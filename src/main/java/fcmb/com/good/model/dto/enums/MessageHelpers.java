package fcmb.com.good.model.dto.enums;

import java.util.HashMap;
import java.util.Map;

public enum MessageHelpers {

    RECORD_NOT_FOUND("Record with specified credential not found", "404"),
    RECORD_EXISTED("Record with specified credential Existed", "404"),
    OKAY("Operation processed successfully", "200"),
    DELETE_SUCCESSFUL("Record deleted successfully", "200"),
    UPDATE_SUCCESSFUL("Record Updated successfully", "200"),
    CREATE_SUCCESSFUL("Record Created successfully", "200");




    private static final Map<String, MessageHelpers> map = new HashMap<>();


    static {
        for (MessageHelpers e : values()) {
            map.put(e.name(), e);
        }
    }

    public final String message;
    public final String code;

    private MessageHelpers(String message, String code) {
        this.message=message;
        this.code=code;

    }
}
