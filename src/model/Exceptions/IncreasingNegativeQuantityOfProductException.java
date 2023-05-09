package Exceptions;

public class IncreasingNegativeQuantityOfProductException extends Exception {
    public IncreasingNegativeQuantityOfProductException(){
        super("You cannot increase negatively the quantity of a product");
    }
}
