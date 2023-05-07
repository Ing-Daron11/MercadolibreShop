import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static Inventory inventory; //Conexión con la clase Inventory que es la controladora y contenedora

    public static void main(String[] args) throws ParseException {
        String msj = "";
        inventory = new Inventory();
        int choice;
        do {
            System.out.println("""
                    =========MENU=========\s
                    1. Add product to inventory\s
                    2. Search product in inventory\s
                    3. Increase quantity of Product\s
                    4. Remove product from inventory\s
                    5. List all products in inventory\s
                    6. Add order\s
                    7. List all orders\s
                    0. Exit\s
                    """);
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try {
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
                                        0. BOOKS,
                                        1. ELECTRONICS,
                                        2. CLOTHING_ACCESSORIES,
                                        3. FOOD_BEVERAGES,
                                        4. STATIONERY,
                                        5. SPORTS,
                                        6. BEAUTY_PERSONAL_CARE,
                                        7. TOYS_GAMES""");

                            int categoryIndex = scanner.nextInt();

                            System.out.print("Enter number of purchases: ");
                            int numberOfPurchases = scanner.nextInt();

                            msj = inventory.addProductToInventory(name, description, price, quantityAvailable, categoryIndex, numberOfPurchases);
                            System.out.println(msj);
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    break;
                case 2:
                    System.out.println("""
                            Enter the way which you want to search the product"\s
                                1. BY NAME,
                                2. BY PRICE,
                                3. BY CATEGORY,
                                4. BY NUMBER OF TIMES PURCHASED,
                                """);
                    int selectionOption = scanner.nextInt();
                    switch (selectionOption){
                        case 1:
                            try {
                                System.out.println("Enter the name of the product");
                                String nameProduct = scanner.next();
                                msj = inventory.searchProductByName(nameProduct).toString();
                                System.out.println(msj);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                System.out.println("Enter price of the product");
                                double priceProduct=scanner.nextDouble();
                                msj=inventory.searchProductPrize(priceProduct);
                                System.out.println(msj);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

                            break;
                        case 3:
                            try {
                            System.out.println("Enter the category of the product");
                            System.out.println("""
                            The categories are:\s
                                0. BOOKS,
                                1. ELECTRONICS,
                                2. CLOTHING_ACCESSORIES,
                                3. FOOD_BEVERAGES,
                                4. STATIONERY,
                                5. SPORTS,
                                6. BEAUTY_PERSONAL_CARE,
                                7. TOYS_GAMES""");
                            int categoryOfProduct = scanner.nextInt();
                                ProductCategory category = ProductCategory.values()[categoryOfProduct];
                                msj=inventory.searchProductByCategory(category);
                                System.out.println(msj);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                            break;
                        case 4:
                            try {
                                System.out.println("Enter the times that the product has been purchased");
                                int timesOfPurchase = scanner.nextInt();
                                msj=inventory.searchProductByNumberOfTimesPurchased(timesOfPurchase);
                                System.out.println(msj);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                            break;
                        default:
                            System.out.println("Invalid option");
                    }
                    break;
                case 3:
                    System.out.println("Enter the name of the product which you want to increase its quantity");
                    String nameProduct = scanner.next();
                    try {
                        System.out.println("Enter the quantity to increase");
                        int quantityToIncrease = scanner.nextInt();
                        msj=inventory.increaseProductQuantity(nameProduct,quantityToIncrease);
                        System.out.println(msj);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("Enter the name of the product you want to delete: ");
                    String name = scanner.nextLine();
                    try {
                        msj = inventory.removeProduct(name);
                        System.out.println(msj);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                    break;
                case 5:
                    msj = inventory.listAllProductsInInventory();
                    System.out.println(msj);
                    break;
                case 6:
                    System.out.println("Enter your name: ");
                    String username = scanner.nextLine();
                    try {
                        System.out.println("Enter the date of sell in the format : yyy-MM-dd");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateStr = scanner.next();
                        Date date = sdf.parse(dateStr);
                        System.out.println("Enter the value of the sale");
                        int priceOfSale = scanner.nextInt();
                        msj = inventory.listAllProductsInInventory();
                        System.out.println(msj);
                        System.out.println("Write the product's names you want to buy separated with // ");
                        String productsToBuy = scanner.nextLine();
                        String[] arrStr = productsToBuy.split("//");
                        if (!msj.equals("")) {
                            msj = inventory.registerOrder(username, date, priceOfSale, arrStr);
                            System.out.println(msj);
                        }
                    }catch (InputMismatchException ex){
                        ex.printStackTrace();
                    }
                    break;
                case 7:

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