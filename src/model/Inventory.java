import Exceptions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class Inventory {
    public ArrayList<Product> listProducts; //Lista de productos
    public ArrayList<Order> listOrder; //Lista de ordenes


    public Inventory() {
        this.listProducts = new ArrayList<>();
        this.listOrder = new ArrayList<>();

    }

    /*----------------Validación para que no se puedan añadir 2 productos con el mismo nombre---------------*/
    public int searchIndexProduct(String name) {
        int index = -1;
        for (int i = 0; i < listProducts.size(); i++) {
            if (listProducts.get(i).getName().equalsIgnoreCase(name)) {
                index = i;
            }
        }
        return index;
    }

    /*--------------------------------------------------------------------------------------------------------*/
    /*----------------------------------------Métodos principales--------------------------------------------*/
    public String addProductToInventory(String name, String description, double price, int quantityAvailable, int category, int numberOfPurchases) throws Exception {
        String msj = "";
        int index = searchIndexProduct(name);
        if (index != -1) {
            throw new ProductAlreadyExistException();
        }
        Product product = new Product(name, description, price, quantityAvailable, category, numberOfPurchases);
        listProducts.add(product);
        msj = "Product: " + name + " was added to inventory successfully";
        return msj;
    }

    public Product searchProductByName(String productName) throws InventoryEmptyException, ProductNotFoundException {
        Product theProduct;
        ArrayList<Product> productsOrderByName = orderProductsByName(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByName.size() - 1;
        int position = binarySearchByName(productName, startingNumber, endingNumber, productsOrderByName);
        theProduct = productsOrderByName.get(position);
        return theProduct;
    }

    public String searchProductPrize(double productPrice) throws InventoryEmptyException, ProductNotFoundException {
        String msj = "";
        ArrayList<Product> productsOrderByPrice = orderProductsByPrice(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByPrice.size() - 1;
        String[] line = binarySearchByPrice(productPrice, startingNumber, endingNumber, productsOrderByPrice).split("//");
        for (int i = 1; i < line.length; i++) {
            msj = msj + productsOrderByPrice.get(Integer.parseInt(line[i])).toString() + "\n";
        }
        return msj;
    }

    public String searchProductByCategory(ProductCategory productCategory) throws InventoryEmptyException, ProductNotFoundException {
        String msj = "";
        ArrayList<Product> productsOrderByCategory = orderProductsByCategory(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByCategory.size() - 1;
        String[] line = binarySearchByCategory(productCategory, startingNumber, endingNumber, productsOrderByCategory).split("//");
        for (int i = 1; i < line.length; i++) {
            msj = msj + productsOrderByCategory.get(Integer.parseInt(line[i])).toString() + "\n";
        }
        return msj;
    }

    public String searchProductByNumberOfTimesPurchased(int numberOfTimesPurchased) throws InventoryEmptyException, ProductNotFoundException {
        String msj = "";
        ArrayList<Product> productsOrderByNumberOfTimesPurchase = orderProductsByNumberOfTimesPurchasedAscending(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByNumberOfTimesPurchase.size() - 1;
        String[] line = binarySearchByNumberOfTimesPurchased(numberOfTimesPurchased, startingNumber, endingNumber, productsOrderByNumberOfTimesPurchase).split("//");
        for (int i = 1; i < line.length; i++) {
            msj = msj + productsOrderByNumberOfTimesPurchase.get(Integer.parseInt(line[i])).toString() + "\n";
        }
        return msj;
    }

    public String increaseProductQuantity(String productName, int quantityToIncrease) throws Exception {
        String msj = "";
        ArrayList<Product> productsOrderByName = orderProductsByName(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByName.size() - 1;
        int position = binarySearchByName(productName, startingNumber, endingNumber, productsOrderByName);
        productsOrderByName.get(position).addQuantityOfProduct(quantityToIncrease);
        msj = "The product " + productName + " now has " + productsOrderByName.get(position).getQuantityAvailable() + " units available";
        return msj;
    }

    public String removeProduct(String name) throws ProductNotFoundException, InventoryEmptyException {
        String msj = "";
        ArrayList<Product> productsOrderByName = orderProductsByName(listProducts);
        int index = binarySearchByName(name, 0, productsOrderByName.size() - 1, productsOrderByName);
        listProducts.remove(index);
        msj = "The product: " + name + " was succesfully deleted";
        return msj;
    }

    public String listAllProductsInInventory() throws InventoryEmptyException {
        String msj;
        if (listProducts.isEmpty()) {
            throw new InventoryEmptyException();
        } else {
            msj = "\n=== INVENTORY ===";
            for (int i = 0; i < listProducts.size(); i++) {
                msj += "\n" + i + " " + listProducts.get(i).toString();
            }
            return msj;
        }
    }

    public String registerOrder(String username, Date date, int priceOfSale, String[] productsToBuy) throws InventoryEmptyException, ProductNotFoundException, NotAvailableToSellException {
        String msj = "";
        ArrayList<Product> productsOrderByName = orderProductsByName(listProducts);
        int startingNumber = 0;
        int endingNumber = productsOrderByName.size() - 1;
        for (int i = 0; i < productsToBuy.length; i++) {
            binarySearchByName(productsToBuy[i], startingNumber, endingNumber, productsOrderByName);
        }
        for (int i = 0; i < productsToBuy.length; i++) {
            int index = binarySearchByName(productsToBuy[i], startingNumber, endingNumber, productsOrderByName);

            productsOrderByName.get(index).sellProduct(1);
        }
        msj = "The order was succefully registered";
        Order newOrder = new Order(username, date, priceOfSale, productsToBuy);
        listOrder.add(newOrder);
        return msj;
    }

    public String listAllOrdersInInventory() throws NoOrdersRegisteredException {
        String msj;
        if (listOrder.isEmpty()) {
            throw new NoOrdersRegisteredException();
        } else {
            msj = "\n=== ORDERS ===";
            for (int i = 0; i < listOrder.size(); i++) {
                msj += "\n" + i + " " + listOrder.get(i).toString();
            }
            return msj;
        }
    }

    public String searchOrderByUserName(String userName) throws OrderNotFoundException, NoOrdersRegisteredException {
        String msj = "";
        ArrayList<Order> ordersOrderedByUserName = orderOrdersByUserName(listOrder);
        int startingNumber = 0;
        int endingNumber = ordersOrderedByUserName.size() - 1;
        String[] line = binarySearchByUserName(userName, startingNumber, endingNumber, ordersOrderedByUserName).split("//");
        for (int i = 1; i < line.length; i++) {
            msj = msj + ordersOrderedByUserName.get(Integer.parseInt(line[i])).toString() + "\n";
        }
        return msj;
    }

    public String searchOrderTotalPrice(double totalPrice) throws OrderNotFoundException, NoOrdersRegisteredException {
        String msj = "";
        ArrayList<Order> ordersOrderedByTotalPriceOfSale = orderOrdersByTotalPrice(listOrder);
        int startingNumber = 0;
        int endingNumber = ordersOrderedByTotalPriceOfSale.size() - 1;
        String[] line = binarySearchByTotalPriceOfSale(totalPrice, startingNumber, endingNumber, ordersOrderedByTotalPriceOfSale).split("//");
        for (int i = 1; i < line.length; i++) {
            msj = msj + ordersOrderedByTotalPriceOfSale.get(Integer.parseInt(line[i])).toString() + "\n";
        }
        return msj;
    }

    public String searchOrderByDate(Date date) throws OrderNotFoundException, NoOrdersRegisteredException {
        String msj = "";
        ArrayList<Order> ordersOrderedByDateOfPurchase = orderOrdersByDate(listOrder);
        int startingNumber = 0;
        int endingNumber = ordersOrderedByDateOfPurchase.size() - 1;
        String[] line = binarySearchByDateOfSale(date, startingNumber, endingNumber, ordersOrderedByDateOfPurchase).split("//");
        for (int i = 1; i < line.length; i++) {
            msj = msj + ordersOrderedByDateOfPurchase.get(Integer.parseInt(line[i])).toString() + "\n";
        }
        return msj;
    }

    public String bsRangeNumericalValuesPrice(int maxnumber, int minNumber, int typeOrdering) throws MinValueMajorThanMaxValueException, InventoryEmptyException, ProductNotFoundException {
        if (minNumber > maxnumber) {
            throw new MinValueMajorThanMaxValueException();
        }
        String msj = "";
        ArrayList<Product> productsOrderByPrice;
        productsOrderByPrice = orderProductsByPrice(listProducts);
        int staringNumber = binarySearchByPriceMIN(minNumber, 0, productsOrderByPrice.size() - 1, productsOrderByPrice); //Buscamos el limite inferior
        int lastNumber = binarySearchByPriceMAX(maxnumber, 0, productsOrderByPrice.size() - 1, productsOrderByPrice);//Buscamos el limite superior
        if (productsOrderByPrice.get(lastNumber).getPrice() > maxnumber||staringNumber>lastNumber) {
            throw new ProductNotFoundException();
        } else {
            List<Product> acotaArray = listProducts.subList(staringNumber, lastNumber + 1);//Luego se saca la sublista de todos esos productos
            if (typeOrdering == 1) {
                for (int i = 0; i < acotaArray.size(); i++) {
                    msj += acotaArray.get(i).toString();
                }
            } else {
                Stack<Product> stackReverse = new Stack<>();
                for (int i = 0; i < acotaArray.size(); i++) {
                    stackReverse.push(acotaArray.get(i));
                }
                while (!stackReverse.isEmpty()) {
                    msj += stackReverse.pop().toString();
                }
            }
        }
        return msj;
    }

    public String bsRangeNumericalValuesQuantity(int maxnumber, int minNumber, int typeOrdering) throws MinValueMajorThanMaxValueException, InventoryEmptyException, ProductNotFoundException {
        if (minNumber > maxnumber) {
            throw new MinValueMajorThanMaxValueException();
        }
        String msj = "";
        ArrayList<Product> productsOrderByQuantity;
        productsOrderByQuantity = orderProductsByQuantityAscending(listProducts);
        int staringNumber = binarySearchByQuantityAvailableMIN(minNumber, 0, productsOrderByQuantity.size() - 1, productsOrderByQuantity); //Buscamos el limite inferior
        int lastNumber = binarySearchByQuantityAvailableMAX(maxnumber, 0, productsOrderByQuantity.size() - 1, productsOrderByQuantity);//Buscamos el limite superior
        if (productsOrderByQuantity.get(lastNumber).getQuantityAvailable() > maxnumber||staringNumber>lastNumber) {
            throw new ProductNotFoundException();
        } else {
            List<Product> acotaArray = listProducts.subList(staringNumber, lastNumber + 1);//Luego se saca la sublista de todos esos productos
            if (typeOrdering == 1) {
                for (int i = 0; i < acotaArray.size(); i++) {
                    msj += acotaArray.get(i).toString();
                }
            } else {
                Stack<Product> stackReverse = new Stack<>();
                for (int i = 0; i < acotaArray.size(); i++) {
                    stackReverse.push(acotaArray.get(i));
                }
                while (!stackReverse.isEmpty()) {
                    msj += stackReverse.pop().toString();
                }
            }
        }
        return msj;
    }

    public String bsRangeNumericalValuesPurchaseTimes(int maxnumber, int minNumber, int typeOrdering) throws MinValueMajorThanMaxValueException, InventoryEmptyException, ProductNotFoundException {
        if (minNumber > maxnumber) {
            throw new MinValueMajorThanMaxValueException();
        }
        String msj = "";
        ArrayList<Product> productsOrderByPurchaseTimes;
        productsOrderByPurchaseTimes = orderProductsByNumberOfTimesPurchasedAscending(listProducts);
        int staringNumber = binarySearchByPurchaseTimesMIN(minNumber, 0, productsOrderByPurchaseTimes.size() - 1, productsOrderByPurchaseTimes); //Buscamos el limite inferior
        int lastNumber = binarySearchByPurchaseTimesMAX(maxnumber, 0, productsOrderByPurchaseTimes.size() - 1, productsOrderByPurchaseTimes);//Buscamos el limite superior
        if (productsOrderByPurchaseTimes.get(lastNumber).getNumberOfPurchases() > maxnumber||staringNumber>lastNumber) {
            throw new ProductNotFoundException();
        } else {
            List<Product> acotaArray = listProducts.subList(staringNumber, lastNumber + 1);//Luego se saca la sublista de todos esos productos
            if (typeOrdering == 1) {
                for (int i = 0; i < acotaArray.size(); i++) {
                    msj += acotaArray.get(i).toString();
                }
            } else {
                Stack<Product> stackReverse = new Stack<>();
                for (int i = 0; i < acotaArray.size(); i++) {
                    stackReverse.push(acotaArray.get(i));
                }
                while (!stackReverse.isEmpty()) {
                    msj += stackReverse.pop().toString();
                }
            }
        }
        return msj;
    }
    public String bsRangeByName(char minLetter, char maxLetter, int typeOrdering) throws MinValueMajorThanMaxValueException, InventoryEmptyException, ProductNotFoundException {
        if ((int) minLetter > (int) maxLetter) {
            throw new MinValueMajorThanMaxValueException();
        }
        String msj = "";
        ArrayList<Product> productsOrderByPurchaseTimes;
        productsOrderByPurchaseTimes = orderProductsByName(listProducts);
        int staringNumber = binarySearchByLetterMIN(minLetter, 0, productsOrderByPurchaseTimes.size() - 1, productsOrderByPurchaseTimes); //Buscamos el limite inferior
        int lastNumber = binarySearchByLetterMAX(maxLetter, 0, productsOrderByPurchaseTimes.size() - 1, productsOrderByPurchaseTimes);//Buscamos el limite superior
        if (productsOrderByPurchaseTimes.get(lastNumber).getNumberOfPurchases() > maxLetter||staringNumber>lastNumber) {
            throw new ProductNotFoundException();
        } else {
            List<Product> acotaArray = listProducts.subList(staringNumber, lastNumber + 1);//Luego se saca la sublista de todos esos productos
            if (typeOrdering == 1) {
                for (int i = 0; i < acotaArray.size(); i++) {
                    msj += acotaArray.get(i).toString();
                }
            } else {
                Stack<Product> stackReverse = new Stack<>();
                for (int i = 0; i < acotaArray.size(); i++) {
                    stackReverse.push(acotaArray.get(i));
                }
                while (!stackReverse.isEmpty()) {
                    msj += stackReverse.pop().toString();
                }
            }
        }
        return msj;
    }

    /* -------------------------- Metodos auxiliares (Ordenamiento)-----------------------------*/

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
                if ((arrayStr.get(i).getPrice()) > (arrayStr.get(i + 1).getPrice())) {
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

    public ArrayList<Product> orderProductsByQuantityAscending(ArrayList<Product> arrayStr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayStr.size() - 1; i++) {
                if ((arrayStr.get(i).getQuantityAvailable()) > (arrayStr.get(i + 1).getQuantityAvailable())) {
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
                if ((arrayStr.get(i).getCategory().compareTo(arrayStr.get(i + 1).getCategory())) > 0) {
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

    public ArrayList<Product> orderProductsByNumberOfTimesPurchasedAscending(ArrayList<Product> arrayStr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayStr.size() - 1; i++) {
                if ((arrayStr.get(i).getNumberOfPurchases()) > (arrayStr.get(i + 1).getNumberOfPurchases())) {
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

    public ArrayList<Order> orderOrdersByUserName(ArrayList<Order> arrayStr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayStr.size() - 1; i++) {
                if ((arrayStr.get(i).getUsername()).compareTo(arrayStr.get(i + 1).getUsername()) > 0) {
                    swapped = true;
                    Order current = arrayStr.get(i);
                    Order next = arrayStr.get(i + 1);
                    arrayStr.set(i, next);
                    arrayStr.set(i + 1, current);
                }
            }
        } while (swapped);
        return arrayStr;
    }

    public ArrayList<Order> orderOrdersByTotalPrice(ArrayList<Order> arrayStr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayStr.size() - 1; i++) {
                if ((arrayStr.get(i).getPriceOfSale()) > (arrayStr.get(i + 1).getPriceOfSale())) {
                    swapped = true;
                    Order current = arrayStr.get(i);
                    Order next = arrayStr.get(i + 1);
                    arrayStr.set(i, next);
                    arrayStr.set(i + 1, current);
                }
            }
        } while (swapped);
        return arrayStr;
    }

    public ArrayList<Order> orderOrdersByDate(ArrayList<Order> arrayStr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arrayStr.size() - 1; i++) {
                if ((arrayStr.get(i).getDate().compareTo(arrayStr.get(i + 1).getDate()) > 0)) {
                    swapped = true;
                    Order current = arrayStr.get(i);
                    Order next = arrayStr.get(i + 1);
                    arrayStr.set(i, next);
                    arrayStr.set(i + 1, current);
                }
            }
        } while (swapped);
        return arrayStr;
    }

    /*--------------------------------Métodos auxiliares (Busqueda binaria)----------------------------------------*/
    public static int binarySearchByName(String goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (arrOrder.get(theMiddle).getName().equalsIgnoreCase(goal)) {
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
        String msj = "";
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (arrOrder.get(theMiddle).getPrice() == goal) {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getPrice() == goal) {
                theMiddle--;
            }
            while (theMiddle < arrOrder.size() && arrOrder.get(theMiddle).getPrice() == goal) {
                msj = msj + "//" + theMiddle;
                theMiddle++;
            }
            return msj;
        }
        if (startingNumber > endingNumber) {
            throw new ProductNotFoundException();
        }
        if (arrOrder.get(theMiddle).getPrice() < goal) {
            return binarySearchByPrice(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            return binarySearchByPrice(goal, startingNumber, theMiddle - 1, arrOrder);
        }
    }

    public static String binarySearchByCategory(ProductCategory goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        String msj = "";
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (arrOrder.get(theMiddle).getCategory().equals(goal)) {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getCategory().equals(goal)) {
                theMiddle--;
            }
            while (theMiddle < arrOrder.size() && arrOrder.get(theMiddle).getCategory().equals(goal)) {
                msj = msj + "//" + theMiddle;
                theMiddle++;
            }
            return msj;
        }
        if (startingNumber > endingNumber) {
            throw new ProductNotFoundException();
        }
        if (arrOrder.get(theMiddle).getCategory().compareTo(goal) < 0) {
            return binarySearchByCategory(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            return binarySearchByCategory(goal, startingNumber, theMiddle - 1, arrOrder);
        }
    }

    public static String binarySearchByNumberOfTimesPurchased(double goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        String msj = "";
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (arrOrder.get(theMiddle).getNumberOfPurchases() == goal) {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getNumberOfPurchases() == goal) {
                theMiddle--;
            }
            while (theMiddle < arrOrder.size() && arrOrder.get(theMiddle).getNumberOfPurchases() == goal) {
                msj = msj + "//" + theMiddle;
                theMiddle++;
            }
            return msj;
        }
        if (startingNumber > endingNumber) {
            throw new ProductNotFoundException();
        }
        if (arrOrder.get(theMiddle).getNumberOfPurchases() < goal) {
            return binarySearchByNumberOfTimesPurchased(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            return binarySearchByNumberOfTimesPurchased(goal, startingNumber, theMiddle - 1, arrOrder);
        }
    }

    public static String binarySearchByUserName(String goal, int startingNumber, int endingNumber, ArrayList<Order> arrOrder) throws NoOrdersRegisteredException, OrderNotFoundException {
        String msj = "";
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new NoOrdersRegisteredException();
        }
        if (arrOrder.get(theMiddle).getUsername().equals(goal)) {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getUsername().equals(goal)) {
                theMiddle--;
            }
            while (theMiddle < arrOrder.size() && arrOrder.get(theMiddle).getUsername().equals(goal)) {
                msj = msj + "//" + theMiddle;
                theMiddle++;
            }
            return msj;
        }
        if (startingNumber > endingNumber) {
            throw new OrderNotFoundException();
        }
        if (arrOrder.get(theMiddle).getUsername().compareTo(goal) < 0) {
            return binarySearchByUserName(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            return binarySearchByUserName(goal, startingNumber, theMiddle - 1, arrOrder);
        }
    }

    public static String binarySearchByTotalPriceOfSale(double goal, int startingNumber, int endingNumber, ArrayList<Order> arrOrder) throws NoOrdersRegisteredException, OrderNotFoundException {
        String msj = "";
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new NoOrdersRegisteredException();
        }
        if (arrOrder.get(theMiddle).getPriceOfSale() == goal) {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getPriceOfSale() == goal) {
                theMiddle--;
            }
            while (theMiddle < arrOrder.size() && arrOrder.get(theMiddle).getPriceOfSale() == goal) {
                msj = msj + "//" + theMiddle;
                theMiddle++;
            }
            return msj;
        }
        if (startingNumber > endingNumber) {
            throw new OrderNotFoundException();
        }
        if (arrOrder.get(theMiddle).getPriceOfSale() < goal) {
            return binarySearchByTotalPriceOfSale(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            return binarySearchByTotalPriceOfSale(goal, startingNumber, theMiddle - 1, arrOrder);
        }
    }

    public static String binarySearchByDateOfSale(Date goal, int startingNumber, int endingNumber, ArrayList<Order> arrOrder) throws NoOrdersRegisteredException, OrderNotFoundException {
        String msj = "";
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new NoOrdersRegisteredException();
        }
        if (arrOrder.get(theMiddle).getDate().equals(goal)) {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getDate().equals(goal)) {
                theMiddle--;
            }
            while (theMiddle < arrOrder.size() && arrOrder.get(theMiddle).getDate().equals(goal)) {
                msj = msj + "//" + theMiddle;
                theMiddle++;
            }
            return msj;
        }
        if (startingNumber > endingNumber) {
            throw new OrderNotFoundException();
        }
        if (arrOrder.get(theMiddle).getDate().compareTo(goal) < 0) {
            return binarySearchByDateOfSale(goal, theMiddle + 1, endingNumber, arrOrder);
        } else {
            return binarySearchByDateOfSale(goal, startingNumber, theMiddle - 1, arrOrder);
        }
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

    public static int binarySearchByPriceMIN(int goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getPrice() <= goal) {
            return binarySearchByPriceMIN(goal, theMiddle + 1, endingNumber, arrOrder);
        } else if (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getPrice() > goal) {
            return binarySearchByPriceMIN(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getPrice() > goal && arrOrder.get(theMiddle).getPrice() < goal) {
            return theMiddle + 1;
        }
        if (arrOrder.get(theMiddle).getPrice() < goal) {
            throw new ProductNotFoundException();
        } else {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getPrice() == goal) {
                theMiddle--;
            }
            return theMiddle;
        }
    }

    public static int binarySearchByPriceMAX(int goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getPrice() <= goal) {
            return binarySearchByPriceMAX(goal, theMiddle + 1, endingNumber, arrOrder);
        } else if (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getPrice() > goal) {
            return binarySearchByPriceMAX(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        return theMiddle;
    }

    public static int binarySearchByQuantityAvailableMIN(int goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getQuantityAvailable() <= goal) {
            return binarySearchByQuantityAvailableMIN(goal, theMiddle + 1, endingNumber, arrOrder);
        } else if (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getQuantityAvailable() > goal) {
            return binarySearchByQuantityAvailableMIN(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getQuantityAvailable() > goal && arrOrder.get(theMiddle).getQuantityAvailable() < goal) {
            return theMiddle + 1;
        }
        if (arrOrder.get(theMiddle).getQuantityAvailable() < goal) {
            throw new ProductNotFoundException();
        } else {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getQuantityAvailable() == goal) {
                theMiddle--;
            }
            return theMiddle;
        }
    }

    public static int binarySearchByQuantityAvailableMAX(int goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getQuantityAvailable() <= goal) {
            return binarySearchByQuantityAvailableMAX(goal, theMiddle + 1, endingNumber, arrOrder);
        } else if (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getQuantityAvailable() > goal) {
            return binarySearchByQuantityAvailableMAX(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        return theMiddle;
    }


    public static int binarySearchByPurchaseTimesMIN(int goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getNumberOfPurchases() <= goal) {
            return binarySearchByPurchaseTimesMIN(goal, theMiddle + 1, endingNumber, arrOrder);
        } else if (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getNumberOfPurchases() > goal) {
            return binarySearchByPurchaseTimesMIN(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getNumberOfPurchases() > goal && arrOrder.get(theMiddle).getNumberOfPurchases() < goal) {
            return theMiddle + 1;
        }
        if (arrOrder.get(theMiddle).getNumberOfPurchases() < goal) {
            throw new ProductNotFoundException();
        } else {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getNumberOfPurchases() == goal) {
                theMiddle--;
            }
            return theMiddle;
        }
    }

    public static int binarySearchByPurchaseTimesMAX(int goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getNumberOfPurchases() <= goal) {
            return binarySearchByPurchaseTimesMAX(goal, theMiddle + 1, endingNumber, arrOrder);
        } else if (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getNumberOfPurchases() > goal) {
            return binarySearchByPurchaseTimesMAX(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        return theMiddle;
    }
    public static int binarySearchByLetterMIN(char goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getName().charAt(0) <= goal) {
            return binarySearchByLetterMIN(goal, theMiddle + 1, endingNumber, arrOrder);
        } else if (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getName().charAt(0)> goal) {
            return binarySearchByLetterMIN(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getName().charAt(0) > goal && arrOrder.get(theMiddle).getName().charAt(0) < goal) {
            return theMiddle + 1;
        }
        if (arrOrder.get(theMiddle).getName().charAt(0) < goal) {
            throw new ProductNotFoundException();
        } else {
            while (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getName().charAt(0) == goal) {
                theMiddle--;
            }
            return theMiddle;
        }
    }
    public static int binarySearchByLetterMAX(int goal, int startingNumber, int endingNumber, ArrayList<Product> arrOrder) throws ProductNotFoundException, InventoryEmptyException {
        int theMiddle = (startingNumber + endingNumber) / 2;
        if (arrOrder.isEmpty()) {
            throw new InventoryEmptyException();
        }
        if (theMiddle + 1 != arrOrder.size() && arrOrder.get(theMiddle + 1).getName().charAt(0) <= goal) {
            return binarySearchByLetterMAX(goal, theMiddle + 1, endingNumber, arrOrder);
        } else if (theMiddle - 1 != -1 && arrOrder.get(theMiddle - 1).getName().charAt(0) > goal) {
            return binarySearchByLetterMAX(goal, startingNumber, theMiddle - 1, arrOrder);
        }
        return theMiddle;
    }
}
