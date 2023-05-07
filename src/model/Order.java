
import java.util.Arrays;
import java.util.Date;

public class Order {
    private String username;
    private Date date;
    private int priceOfSale;
    private String[] listProducts;


    public Order(String username, Date date, int priceOfSale, String[] listProducts) {
        this.username = username;
        this.date = date;
        this.priceOfSale = priceOfSale;
        this.listProducts = listProducts;
    }

    //-----------------------Getters and Setters---------------------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String[] getListProducts() {
        return listProducts;
    }

    public void setListProducts(String[] listProducts) {
        this.listProducts = listProducts;
    }

    public int getPriceOfSale() {
        return priceOfSale;
    }

    public void setPriceOfSale(int priceOfSale) {
        this.priceOfSale = priceOfSale;
    }

    @Override
    public String toString() {
        return "Order{" +
                "username='" + username + '\'' +
                ", date=" + date +
                ", priceOfSale=" + priceOfSale +
                ", listProducts=" + Arrays.toString(listProducts) +
                '}';
    }
}
