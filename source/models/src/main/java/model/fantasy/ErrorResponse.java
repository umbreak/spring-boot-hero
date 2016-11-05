package model.fantasy;

/**
 * Created by didac on 19.05.16.
 */
public class ErrorResponse {

    public static enum Error{
        HERO_ALREADY_EXISTS,
        PRECONDITIONS_MISSING,
        WRONG_PARAMETERS,
        HERO_NOT_FOUND;

    }
    private Error error;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(Error error) {
        this.error = error;
    }

    public ErrorResponse(Error error, String message) {
        this.error = error;
        this.message = message;
    }

    public Error getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
