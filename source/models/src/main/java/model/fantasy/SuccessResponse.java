package model.fantasy;

/**
 * Created by didac on 19.05.16.
 */
public class SuccessResponse {

    public static enum Code {
        HERO_CREATED;
    }
    private Code code;
    private String message;

    public SuccessResponse() {
    }

    public SuccessResponse(Code code) {
        this.code = code;
    }

    public SuccessResponse(Code code, String message) {
        this.code = code;
        this.message = message;
    }

    public Code getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
