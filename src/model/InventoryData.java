import java.util.ArrayList;

public class InventoryData {
    public static ArrayList<Product> listProducts;
    public static ArrayList<Order> listOrder;

    public InventoryData(ArrayList<Product> listProducts, ArrayList<Order> listOrder) {
        InventoryData.listProducts = listProducts;
        InventoryData.listOrder = listOrder;
    }
}
