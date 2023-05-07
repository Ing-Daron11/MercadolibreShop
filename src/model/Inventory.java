import Exceptions.CategoryDoesnotExistException;
import Exceptions.InventoryEmptyException;
import Exceptions.ProductAlreadyExistException;
import Exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;

public class Inventory {
    public ArrayList<Product> listProducts; //Lista de productos
    public ArrayList<Order> listOrder; //Lista de ordenes
    private Product product; //Conexion con la clase Product
    private Order order;


    public Inventory() {
        this.listProducts = new ArrayList<>();
        this.listOrder = new ArrayList<>();

    }

    public String addProductToInventory(String name, String description, double price, int quantityAvailable, int category, int numberOfPurchases) throws ProductAlreadyExistException {
        String msj="";
        int index = searchIndexProduct(name);
        if (index!=-1){
            throw new ProductAlreadyExistException();
        }
        try {
            Product product = new Product(name, description, price, quantityAvailable, category, numberOfPurchases);
            listProducts.add(product);
            msj="Product: " + name + " was added to inventory successfully";
        }catch (CategoryDoesnotExistException | InputMismatchException ex){
            ex.printStackTrace();
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

    public Product searchProductByName(String productName) throws Exception {
        Product theProduct;
        ArrayList<Product> productsOrderByName = orderProductsByName(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByName.size() - 1;
        int position = binarySearchByName(productName, startingNumber, endingNumber, productsOrderByName);
        theProduct = productsOrderByName.get(position);
        return theProduct;
    }

    public String searchProductPrize(double productPrice) throws Exception {
        String msj="";
        ArrayList<Product> productsOrderByPrice = orderProductsByPrice(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByPrice.size() - 1;
        String[] line=binarySearchByPrice(productPrice, startingNumber, endingNumber, productsOrderByPrice).split("//");
        for (int i=1; i<line.length;i++) {
            msj = msj + productsOrderByPrice.get(Integer.parseInt(line[i])).toString() + "\n";
        }
        return msj;
    }
    public String searchProductByCategory(ProductCategory productCategory) {
        String msj="";
        ArrayList<Product> productsOrderByCategory = orderProductsByCategory(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByCategory.size() - 1;
        try {
            String[] line=binarySearchByCategory(productCategory, startingNumber, endingNumber, productsOrderByCategory).split("//");
            for (int i=1; i<line.length;i++){
                msj = msj+productsOrderByCategory.get(Integer.parseInt(line[i])).toString()+"\n";
            }
        } catch (ProductNotFoundException | InventoryEmptyException ex) {
            ex.printStackTrace();
        }
        return msj;
    }

    public String searchProductByNumberOfTimesPurchased(int numberOfTimesPurchased) {
        String msj="";
        ArrayList<Product> productsOrderByNumberOfTimesPurchase = orderProductsByNumberOfTimesPurchased(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByNumberOfTimesPurchase.size() - 1;
        try {
            String[] line=binarySearchByNumberOfTimesPurchased(numberOfTimesPurchased, startingNumber, endingNumber, productsOrderByNumberOfTimesPurchase).split("//");
            for (int i=1; i<line.length;i++){
                msj = msj+productsOrderByNumberOfTimesPurchase.get(Integer.parseInt(line[i])).toString()+"\n";
            }
        } catch (ProductNotFoundException | InventoryEmptyException ex) {
            ex.printStackTrace();
        }
        return msj;
    }
    public String removeProduct(String name) throws ProductNotFoundException, InventoryEmptyException {
        String msj = "";
        ArrayList<Product> productsOrderByName = orderProductsByName(listProducts);
        int index =binarySearchByName(name, 0, productsOrderByName.size() - 1, productsOrderByName);
        listProducts.remove(index);
        msj = "The product: " + name + " was succesfully deleted";
        return msj;
    }

    public String listAllProductsInInventory(){
        String msj="";
        if (listProducts.isEmpty()) {
            try {
                throw new InventoryEmptyException();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        } else {
            msj = "\n=== INVENTORY ===";
            for (int i = 0; i < listProducts.size(); i++) {
                msj += i + " " + listProducts.get(i).toString() + " \n";
            }
            return msj;
        }
        return msj;
    }

    public String registerOrder(String username, Date date, int priceOfSale, String[] productsToBuy) {
        String msj = "";
        ArrayList<Product> productsOrderByName = orderProductsByName(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByName.size() - 1;
        try {
            for (int i = 0; i < productsToBuy.length; i++) {
                binarySearchByName(productsToBuy[i], startingNumber, endingNumber, productsOrderByName);
            }
            for (int i = 0; i < productsToBuy.length; i++) {
                int index = binarySearchByName(productsToBuy[i], startingNumber, endingNumber, productsOrderByName);
                productsOrderByName.get(index).sellProduct(1);
            }
            msj = "The order was succefully registered";
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Order newOrder = new Order(username, date,priceOfSale,productsToBuy);
        listOrder.add(newOrder);
        return msj;
    }

    public String increaseProductQuantity(String productName, int quantityToIncrease) throws Exception{
        String msj="";
        ArrayList<Product> productsOrderByName = orderProductsByName(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByName.size() - 1;
        int position = binarySearchByName(productName, startingNumber, endingNumber, productsOrderByName);
        productsOrderByName.get(position).addQuantityOfProduct(quantityToIncrease);
        msj = "The product " + productName + " now has " +productsOrderByName.get(position).getQuantityAvailable()+ " units available";
        return msj;
    }

    //Metodo de ordenamiento de String, Compara en base al sistema UNICODE.

    public ArrayList<Product> orderProductsByName(ArrayList<Product> arrayStr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayStr.size() - 1; i++) {
                if ((arrayStr.get(i).getName()).compareTo(arrayStr.get(i + 1).getName()) > 0) {
                    swapped = true;
                    Product current = arrayStr.get(i);
                    Product next = arrayStr.get(i + 1);
                    arrayStr.set(i, next);
                    arrayStr.set(i + 1, current);
                }
            }
        } while (swapped);
        return arrayStr;
    }
    public ArrayList<Product> orderProductsByPrice(ArrayList<Product> arrayStr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayStr.size() - 1; i++) {
                if ((arrayStr.get(i).getPrice())>(arrayStr.get(i + 1).getPrice())) {
                    swapped = true;
                    Product current = arrayStr.get(i);
                    Product next = arrayStr.get(i + 1);
                    arrayStr.set(i, next);
                    arrayStr.set(i + 1, current);
                }
            }
        } while (swapped);
        return arrayStr;
    }
    public ArrayList<Product> orderProductsByCategory(ArrayList<Product> arrayStr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayStr.size() - 1; i++) {
                if ((arrayStr.get(i).getCategory().compareTo(arrayStr.get(i + 1).getCategory()))>0) {
                    swapped = true;
                    Product current = arrayStr.get(i);
                    Product next = arrayStr.get(i + 1);
                    arrayStr.set(i, next);
                    arrayStr.set(i + 1, current);
                }
            }
        } while (swapped);
        return arrayStr;
    }
    public ArrayList<Product> orderProductsByNumberOfTimesPurchased(ArrayList<Product> arrayStr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayStr.size() - 1; i++) {
                if ((arrayStr.get(i).getNumberOfPurchases())>(arrayStr.get(i + 1).getNumberOfPurchases())) {
                    swapped = true;
                    Product current = arrayStr.get(i);
                    Product next = arrayStr.get(i + 1);
                    arrayStr.set(i, next);
                    arrayStr.set(i + 1, current);
                }
            }
        } while (swapped);
        return arrayStr;
    }
    public static int binarySearchByName(String goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if(arrOrder.isEmpty()){
            throw new InventoryEmptyException();
        }
        if (arrOrder.get(theMiddle).getName().equals(goal)) {
            return theMiddle;
        }
        if (startingNumber > endingNumber) {
            throw new ProductNotFoundException();
        }
        if (arrOrder.get(theMiddle).getName().compareTo(goal) < 0) {
            return binarySearchByName(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            return binarySearchByName(goal, startingNumber, theMiddle - 1, arrOrder);
        }
    }
    public static String binarySearchByPrice(double goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        String msj="";
        int theMiddle = (startingNumber + endingNumber) / 2;
        if(arrOrder.isEmpty()){
            throw new InventoryEmptyException();
        }
        if (arrOrder.get(theMiddle).getPrice()==goal) {
            while(theMiddle-1!=-1&&arrOrder.get(theMiddle-1).getPrice()==goal){
               theMiddle--;
            }
            while (theMiddle< arrOrder.size()&&arrOrder.get(theMiddle).getPrice()==goal){
                msj=msj+"//"+theMiddle;
                theMiddle++;
            }
            return msj;
        }
        if (startingNumber > endingNumber) {
            throw new ProductNotFoundException();
        }
        if (arrOrder.get(theMiddle).getPrice()<goal) {
            binarySearchByPrice(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            binarySearchByPrice(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        return "-1";
    }
    public static String binarySearchByCategory(ProductCategory goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        String msj="";
        int theMiddle = (startingNumber + endingNumber) / 2;
        if(arrOrder.isEmpty()){
            throw new InventoryEmptyException();
        }
        if (arrOrder.get(theMiddle).getCategory().equals(goal)) {
            while(theMiddle-1!=-1&&arrOrder.get(theMiddle-1).getCategory().equals(goal)){
                theMiddle--;
            }
            while (theMiddle< arrOrder.size()&&arrOrder.get(theMiddle).getCategory().equals(goal)){
                msj=msj+"//"+theMiddle;
                theMiddle++;
            }
            return msj;
        }
        if (startingNumber > endingNumber) {
            throw new ProductNotFoundException();
        }
        if (arrOrder.get(theMiddle).getCategory().compareTo(goal) < 0) {
            binarySearchByCategory(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            binarySearchByCategory(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        return "-1";
    }
    public static String binarySearchByNumberOfTimesPurchased(double goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        String msj="";
        int theMiddle = (startingNumber + endingNumber) / 2;
        if(arrOrder.isEmpty()){
            throw new InventoryEmptyException();
        }
        if (arrOrder.get(theMiddle).getNumberOfPurchases()==goal) {
            while(theMiddle-1!=-1&&arrOrder.get(theMiddle-1).getNumberOfPurchases()==goal){
                theMiddle--;
            }
            while (theMiddle< arrOrder.size()&&arrOrder.get(theMiddle).getNumberOfPurchases()==goal){
                msj=msj+"//"+theMiddle;
                theMiddle++;
            }
            return msj;
        }
        if (startingNumber > endingNumber) {
            throw new ProductNotFoundException();
        }
        if (arrOrder.get(theMiddle).getNumberOfPurchases()<goal) {
            binarySearchByNumberOfTimesPurchased(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            binarySearchByNumberOfTimesPurchased(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        return "-1";
    }
    public static void bubbleSortInt(ArrayList<Integer> arrayNum) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayNum.size() - 1; i++) {
                if ((arrayNum.get(i)) > (arrayNum.get(i + 1))) {
                    swapped = true;
                    int current = arrayNum.get(i);
                    int next = arrayNum.get(i + 1);
                    arrayNum.set(i, next);
                    arrayNum.set(i + 1, current);
                }
            }
        } while (swapped);
    }
}
