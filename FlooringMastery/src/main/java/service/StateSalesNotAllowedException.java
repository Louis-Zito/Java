package service;

public class StateSalesNotAllowedException extends Exception {

    public StateSalesNotAllowedException(String message){
        super(message);
    }

    public StateSalesNotAllowedException(String message, Throwable cause){
        super(message);
    }
}

