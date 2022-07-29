package com.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

//indicates class will assist all controller in proj
@ControllerAdvice
//indicate able to serve HTTP resp on behalf of other controller
@RestController
//ResponseEntity has exception handling code
public class ToDoControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTRAINT_MESSAGE = "Could not save your item. "
            + "Please ensure it is valid and try again.";

    //annotate method for which exception it will handle
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    //all meths accept 1 java exception & web request
    //returns ResponseEntity<T>
    public final ResponseEntity<Error> handleSqlException(
            SQLIntegrityConstraintViolationException ex, WebRequest request){

        //in meth construct RespEnt as you see fit
        //here return Error/anonymous exception details so build details not revealed
        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    //as many exceptions as needed can be added
    //MAKE SURE only one exception type per meth to avoid ambiguity
}
