import java.util.ArrayList;

public class Inventory {
    private ArrayList<Product> listProducts; //Lista de productos
    private ArrayList<Order> listOrder; //Lista de ordenes
    private Product product; //Conexion con la clase Product
    private Order order;


    public Inventory() {
        this.listProducts = new ArrayList<>();
        this.listOrder = new ArrayList<>();

    }

    public String addProductToInventory(String name, String description, double price, int quantityAvailable, int category, int numberOfPurchases) {
        Product product = new Product(name, description, price, quantityAvailable, category, numberOfPurchases);
        listProducts.add(product);
        return "Product: " + name + " was added to inventory successfully";
    }

    public String searchProduct(String name) {
        String msj = "The product was not found";
        for (Product product : listProducts) {
            if (product.getName().equalsIgnoreCase(name)) {
                msj = product.toString();
            }
        }
        return msj;
    }
    public int searchIndexProduct(String name) {
        int index = -1;
        for (int i = 0; i < listProducts.size(); i++) {
            if(listProducts.get(i).getName().equalsIgnoreCase(name)){
                index = i;
            }
        }
        return index;
    }

    public String removeProduct(String name) {
        String msj = "";
        int index = searchIndexProduct(name);
        if(index != -1){
            listProducts.remove(index);
            msj = "The product: " + name + " was succesfully deleted";
        }else{
            msj = "The product: " + name + " wasn't found";
        }
        return msj;
    }

    public String listAllProductsInInventory(){
        String msj = "\n=== INVENTORY ===";
        for (int i = 0; i < listProducts.size(); i++) {
            msj += i + " " + listProducts.get(i).toString()+ " \n";
        }
        return msj;
    }

    public String registerOrder(String username, String address, String[] productsToBuy){
        String msj ="";
        Order newOrder = new Order(username, address);
        listOrder.add(newOrder);
        for (int i = 0; i < productsToBuy.length; i++) {
            int index = searchIndexProduct(productsToBuy[i]);
            Product product = listProducts.get(index);
            newOrder.addProductToOrder(product);
        }
        msj = "The order was succefully registered";
        return  msj;
    }

    //Método de ordenamiento para enteros
    public static void bubbleSortInt(ArrayList<Integer> arrayNum){
        boolean swapped;
        do{
            swapped = false;
            for (int i = 0; i < arrayNum.size()-1; i++) {
                if((arrayNum.get(i)) > (arrayNum.get(i+1)) ){
                    swapped = true;
                    int current = arrayNum.get(i);
                    int next = arrayNum.get(i+1);
                    arrayNum.set(i, next);
                    arrayNum.set(i+1, current);
                }
            }
        }while (swapped);
    }

    //Metodo de ordenamiento de String, Compara en base al sistema UNICODE.
    public static void bubbleSortString(ArrayList<String> arrayStr){
        boolean swapped;
        do{
            swapped = false;
            for (int i = 0; i < arrayStr.size()-1; i++) {
                if((arrayStr.get(i)).compareTo(arrayStr.get(i+1)) > 0){
                    swapped = true;
                    String current = arrayStr.get(i);
                    String next = arrayStr.get(i+1);
                    arrayStr.set(i, next);
                    arrayStr.set(i+1, current);
                }
            }
        }while (swapped);
    }




}
