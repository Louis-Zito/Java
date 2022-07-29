package com.controllers;
import java.time.LocalDateTime;

//doesn't need to ext Exceptions
//only container to transmit error data via Controller
public class Error {

    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
