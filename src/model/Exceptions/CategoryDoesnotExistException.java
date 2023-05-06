package Exceptions;

public class CategoryDoesnotExistException extends Exception {
    public CategoryDoesnotExistException(){
        super("The category does not exist");
    }
}
