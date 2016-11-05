package hello.rest.exceptions;

import model.fantasy.ErrorResponse;

/**
 * Created by didac on 23.08.16.
 */
public class HeroException extends RuntimeException{
    private final ErrorResponse.Error error;
    public HeroException(String message, ErrorResponse.Error error) {
        super(message);
        this.error=error;

    }

    public HeroException(ErrorResponse.Error error) {
        this.error=error;
    }

    public ErrorResponse.Error getError() {
        return error;
    }
}
