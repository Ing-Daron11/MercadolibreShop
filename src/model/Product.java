import Exceptions.CategoryDoesnotExistException;
import Exceptions.NotAvailableToSellException;

public class Product {
    private String name;
    private String description;
    private double price;
    private int quantityAvailable;
    private ProductCategory category;
    private int numberOfPurchases;

    public Product(String name, String description, double price, int quantityAvailable, int category, int numberOfPurchases) throws CategoryDoesnotExistException {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        if(category>ProductCategory.values().length){
            throw new CategoryDoesnotExistException();
        }
        else{
            this.category = ProductCategory.values()[category];
        }
        this.numberOfPurchases = numberOfPurchases;
    }
    @Override
    public String toString(){
        return "\nName: " + this.name + " \n" +
                "Description: " + this.description + " \n" +
                "Price: " + this.price + " \n" +
                "Quantity: " + this.quantityAvailable + " \n" +
                "Category: " + this.category + " \n" +
                "Purchases: " + this.numberOfPurchases + " \n" +
                "========================= \n";
    }


//-----------------------Getters and Setters---------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public int getNumberOfPurchases() {
        return numberOfPurchases;
    }

    public void setNumberOfPurchases(int numberOfPurchases) {
        this.numberOfPurchases = numberOfPurchases;
    }

    public void sellProduct( int quantityToSell) throws NotAvailableToSellException {
        if (quantityToSell>quantityAvailable) {
            throw new NotAvailableToSellException();
        }
            this.quantityAvailable -= quantityToSell;
            this.numberOfPurchases+= quantityToSell;
        }
}
