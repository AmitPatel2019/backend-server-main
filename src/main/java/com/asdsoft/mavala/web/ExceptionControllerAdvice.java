package com.asdsoft.mavala.web;

import com.asdsoft.mavala.data.ErrorData;
import com.asdsoft.mavala.exception.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = AuthException.class)

    public ResponseEntity<ErrorData> exception(AuthException exception) {
        return new ResponseEntity<>(new ErrorData("0001", "UserMawala is not Authenticated"), HttpStatus.FORBIDDEN);
    }
}
