package Exceptions;

public class NoOrdersRegisteredException extends Exception{
    public NoOrdersRegisteredException(){
        super("Theres no orders in the inventory");
    }
}
