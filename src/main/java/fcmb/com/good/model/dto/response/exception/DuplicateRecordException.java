package fcmb.com.good.model.dto.response.exception;

public class DuplicateRecordException extends RuntimeException{

    private String message;

    public DuplicateRecordException(String message){
        super(message);
        this.message=message;
    }
    public DuplicateRecordException(){

    }

}
