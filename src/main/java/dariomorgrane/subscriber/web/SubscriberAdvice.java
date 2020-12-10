package dariomorgrane.subscriber.web;

import dariomorgrane.subscriber.exception.WebLayerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SubscriberAdvice {

    @ResponseBody
    @ExceptionHandler(WebLayerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String MessageStorageExceptionHandler(RuntimeException ex) {
        return ex.getMessage();
    }

}
