import java.util.ArrayList;
public class Order {
    private String username;
    private String address;
    private ArrayList<Product> listProducts;

    public Order(String username, String address) {
        this.username = username;
        this.address = address;
        this.listProducts = new ArrayList<>();
    }


    public void addProductToOrder(Product product){
        listProducts.add(product);
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

    public ArrayList<Product> getProducts() {
        return listProducts;
    }

    public void setProducts(ArrayList<Product> products) {
        this.listProducts = products;
    }
}
