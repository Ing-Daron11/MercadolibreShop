import java.util.List;

public class Order {
    private String username;
    private String address;
    private List<Product> products;

    public Order(String username, String address, List<Product> products) {
        this.username = username;
        this.address = address;
        this.products = products;
    }

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // ...
}
