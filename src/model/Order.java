import java.util.ArrayList;
import java.util.Arrays;

public class Order {
    private String username;
    private String address;
    private String[] listProducts;
    ;

    public Order(String username, String address, String[] listProducts) {
        this.username = username;
        this.address = address;
        this.listProducts = listProducts;
    }

    //-----------------------Getters and Setters---------------------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getListProducts() {
        return listProducts;
    }

    public void setListProducts(String[] listProducts) {
        this.listProducts = listProducts;
    }

    @Override
    public String toString() {
        return "Order{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", listProducts=" + Arrays.toString(listProducts) +
                '}';
    }
}
