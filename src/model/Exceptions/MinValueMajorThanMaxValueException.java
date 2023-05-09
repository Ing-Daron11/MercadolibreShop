package Exceptions;

public class MinValueMajorThanMaxValueException extends Exception{
    public MinValueMajorThanMaxValueException(){
        super("The min value is major than the max value");
    }
}
