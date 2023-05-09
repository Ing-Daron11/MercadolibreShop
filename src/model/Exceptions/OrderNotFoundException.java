package Exceptions;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(){
        super("The order was not found");
    }
}

