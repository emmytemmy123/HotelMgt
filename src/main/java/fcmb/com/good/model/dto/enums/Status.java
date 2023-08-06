package fcmb.com.good.model.dto.enums;

public enum Status {

    PAID ("paid"),
    TO_BALANCE ("Balance your payment");

    public final String label;
//    public final Integer id;

    Status(String label) {
//        this.id = id;
        this.label = label;
    }


}
