package service;

public class InventoryItemDataValidationException extends Exception{
    public InventoryItemDataValidationException(String message){
        super(message);
    }

    public InventoryItemDataValidationException(String message, Throwable cause){
        super(message, cause);
    }
}
