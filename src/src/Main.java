import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static ArrayList<Product> inventory = new ArrayList<>();
    private static ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {

        int choice;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Add product to inventory");
            System.out.println("2. Search product in inventory");
            System.out.println("3. Remove product from inventory");
            System.out.println("4. List all products in inventory");
            System.out.println("5. Add order");
            System.out.println("6. List all orders");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addProductToInventory();
                    break;
                case 2:
                    searchProductInInventory();
                    break;
                case 3:
                    removeProductFromInventory();
                    break;
                case 4:
                    listAllProductsInInventory();
                    break;
                case 5:
                    addOrder();
                    break;
                case 6:
                    listAllOrders();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 7);
    }

    private static void addProductToInventory() {
        System.out.print("\nEnter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter product description: ");
        String description = scanner.nextLine();

        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity available: ");
        int quantityAvailable = scanner.nextInt();

        System.out.println("Select product category:");
        for (ProductCategory category : ProductCategory.values()) {
            System.out.println(category.ordinal() + ". " + category.name());
        }
        int categoryIndex = scanner.nextInt();
        ProductCategory category = ProductCategory.values()[categoryIndex];

        System.out.print("Enter number of purchases: ");
        int numberOfPurchases = scanner.nextInt();

        Product product = new Product(name, description, price, quantityAvailable, category, numberOfPurchases);
        inventory.add(product);

        System.out.println("\nProduct added to inventory successfully.");
    }


    private static void searchProductInInventory() {
        System.out.print("\nEnter product name: ");
        String name = scanner.nextLine();

        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.println("\n" + product.toString());
                return;
            }
        }

        System.out.println("\nProduct not found in inventory.");
    }

    private static void removeProductFromInventory() {
        System.out.print("\nEnter product name: ");
        String name = scanner.nextLine();

        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                inventory.remove(product);
                System.out.println("\nProduct removed from inventory successfully.");
                return;
            }
        }

        System.out.println("\nProduct not found in inventory.");
    }

    private static void listAllProductsInInventory() {
        if (inventory.isEmpty()) {
            System.out.println("\nInventory is empty.");
        } else {
            System.out.println("\n=== INVENTORY ===");
            for (Product product : inventory) {
                System.out.println(product.toString());
            }
        }
    }


    private static void addOrder(){

    }

    private static void listAllOrders(){

    }


}