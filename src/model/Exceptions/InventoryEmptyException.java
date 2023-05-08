package Exceptions;

public class InventoryEmptyException extends Exception {
    public InventoryEmptyException(){
        super("The inventory is empty, theres no products");
    }
}
