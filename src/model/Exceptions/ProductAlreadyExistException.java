package Exceptions;

public class ProductAlreadyExistException extends Exception {
    public ProductAlreadyExistException(){
        super("The product already exist");
    }
}
