import java.util.Scanner;
public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static Inventory inventory; //Conexión con la clase Inventory que es la controladora y contenedora

    public static void main(String[] args) {
        String msj = "";
        inventory = new Inventory();
        int choice;
        do {
            System.out.println("""
                    =========MENU=========\s
                    1. Add product to inventory\s
                    2. Search product in inventory\s
                    3. Remove product from inventory\s
                    4. List all products in inventory\s
                    5. Add order\s
                    6. List all orders\s
                    0. Exit\s
                    """);
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("\nEnter product name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter product description: ");
                    String description = scanner.nextLine();

                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Enter quantity available: ");
                    int quantityAvailable = scanner.nextInt();

                    System.out.println("""
                            Select product category:\s
                                1. BOOKS,
                                2. ELECTRONICS,
                                3. CLOTHING_ACCESSORIES,
                                4. FOOD_BEVERAGES,
                                5. STATIONERY,
                                6. SPORTS,
                                7. BEAUTY_PERSONAL_CARE,
                                8. TOYS_GAMES""");

                    int categoryIndex = scanner.nextInt();

                    System.out.print("Enter number of purchases: ");
                    int numberOfPurchases = scanner.nextInt();

                    msj = inventory.addProductToInventory(name, description, price, quantityAvailable, categoryIndex, numberOfPurchases);
                    System.out.println(msj);
                    break;
                case 2:
                    System.out.println("Enter the name of the product you want to search for:");
                    name = scanner.nextLine();
                    msj = inventory.searchProduct(name);
                    System.out.println(msj);
                    break;
                case 3:
                    System.out.println("Enter the name of the product you want to delete: ");
                    name = scanner.nextLine();
                    msj = inventory.removeProduct(name);
                    System.out.println(msj);
                    break;
                case 4:
                    msj = inventory.listAllProductsInInventory();
                    System.out.println(msj);
                    break;
                case 5:
                    System.out.println("Enter your name: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter your address: ");
                    String address = scanner.nextLine();
                    System.out.println("How many products you'll buy?: ");
                    int quatityToBuy = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("write the product's names you want to buy: ");
                    msj = inventory.listAllProductsInInventory();
                    System.out.println(msj);
                    String[] productsToBuy = new String[quatityToBuy];
                    for (int i = 0; i < productsToBuy.length; i++) {
                        productsToBuy[i] = scanner.nextLine(); //Se lee el nombre de los productos para luego ser buscados y añadidos en la orden del cliente
                    }
                    msj = inventory.registerOrder(username, address, productsToBuy);
                    System.out.println(msj);
                    break;
                case 6:

                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

}