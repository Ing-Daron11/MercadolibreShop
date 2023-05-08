package Exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(){
        super("There is a product that was not found");
    }
}
