import java.util.ArrayList;

public class InventoryData {
    public ArrayList<Product> listProducts;
    public ArrayList<Order> listOrder;

    public InventoryData(ArrayList<Product> listProducts, ArrayList<Order> listOrder) {
        this.listProducts = listProducts;
        this.listOrder = listOrder;
    }
}
