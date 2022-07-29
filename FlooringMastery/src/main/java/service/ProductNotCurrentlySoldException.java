package service;

public class ProductNotCurrentlySoldException extends Exception {

    public ProductNotCurrentlySoldException(String message){
        super(message);
    }

    public ProductNotCurrentlySoldException(String message, Throwable cause){
        super(message);
    }
}