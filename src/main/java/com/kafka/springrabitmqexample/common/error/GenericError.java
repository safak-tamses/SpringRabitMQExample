package com.kafka.springrabitmqexample.common.error;

import com.kafka.springrabitmqexample.error.DatabaseAccessException;
import com.kafka.springrabitmqexample.error.UserCreateException;
import com.kafka.springrabitmqexample.error.UserNotFoundException;
import com.kafka.springrabitmqexample.model.GenericExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GenericError extends ResponseEntityExceptionHandler {
    //buraya eklicez exceptionları
    @ExceptionHandler({
            UserCreateException.class,
            UserNotFoundException.class,
            DatabaseAccessException.class
    })
    public ResponseEntity<Object> handleCustomException(Exception e) {
        GenericExceptionResponse error = new GenericExceptionResponse(e.getMessage(), new Date());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

}
