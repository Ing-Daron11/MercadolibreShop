package Exceptions;

public class NotAvailableToSellException extends Exception{
    public NotAvailableToSellException(){
        super("Theres no more products to sale");
    }
}
