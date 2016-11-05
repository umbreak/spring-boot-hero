package hello;

import hello.rest.exceptions.HeroException;
import hello.rest.exceptions.HeroNotFoundException;
import model.fantasy.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ErrorsHandling {

    @ResponseBody
    @ExceptionHandler(HeroNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse genericNotFoundHandler(HeroNotFoundException ex) {
        return new ErrorResponse(ErrorResponse.Error.HERO_NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(HeroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse genericErrorHandler(HeroException ex) {
        return new ErrorResponse(ex.getError(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse genericMarshallingErrorHandler(HttpMessageNotReadableException ex) {
        return new ErrorResponse(ErrorResponse.Error.WRONG_PARAMETERS);
    }



}

