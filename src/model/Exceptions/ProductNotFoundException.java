package Exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(){
        super("The product was not found");
    }
}
