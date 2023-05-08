import Exceptions.IncreasingNegativeQuantityOfProductException;
import Exceptions.OrderNotFoundException;
import Exceptions.ProductAlreadyExistException;
import Exceptions.ProductNotFoundException;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class InventoryTest{
    Inventory inventory = new Inventory();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void setupStage1(){

    }
    public void setupStage2() throws Exception {
        inventory.addProductToInventory("Teclado gamer", "Keyboard just for the pros", 100000, 20, 1, 12);
        inventory.addProductToInventory("Hit mango Pet 500ML", "Colombian 100% juice", 2500, 15, 3, 120);
    }
    public void setupStage3() throws Exception {
        inventory.addProductToInventory("Camiseta Polo L", "Very elegant", 30000, 15, 2, 10);
        inventory.addProductToInventory("Marcador Sharpie", "Draw the future", 2500, 8, 4, 24);
        inventory.addProductToInventory("Agua 500 ml", "Refreshing", 2500, 15, 3,14);
    }
    public void setupStage4() throws Exception {
        inventory.addProductToInventory("Leggins", "Be cool and fresh", 25000, 25, 3, 8);
        inventory.addProductToInventory("Soccer ball", "¡Nothing is better!", 30000, 22, 5, 15);
        inventory.addProductToInventory("Pokemón T-shirt","Let’s catch them",27000, 12, 2, 14);

        inventory.registerOrder("Luna", sdf.parse("2023-04-02"),52000,"Leggins//Pokemón T-shirt".split("//"));
        inventory.registerOrder("Mariana",  sdf.parse("2023-04-02"), 25000,"Leggins".split(","));
    }
    @Test
    public void validateAddingAProduct() throws Exception {
        //Arrange
        setupStage1();
        //Act
        inventory.addProductToInventory("PlayStation 8","The mythical PS8", 8000000,5,1, 8);
        //Assert
        assertEquals(inventory.searchIndexProduct("PlayStation 8"),0);
    }
    @Test
    public void validateAddingAProductBadWritten(){
        //Arrange
        setupStage1();
        //Act
        boolean result=false;
        try {
            inventory.addProductToInventory("Imagine Dragons T-shirt", "Fell the power", 70000, Integer.parseInt("XXXX"), 2,45);
            result =true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //Assert
        assertFalse(result);
        assertEquals(inventory.listProducts.size(),0);
    }
    @Test
    public void validateAddingAProductThatAlreadyExist() throws Exception {
        //Arrange
        setupStage2();
        //Act
        boolean result = false;
        try {
            inventory.addProductToInventory("Teclado gamer", "New keyboard", 30000, 20, 1, 14);
            result =true;
        }catch (ProductAlreadyExistException ex){
            ex.printStackTrace();
        }
        //Assert
        assertFalse(result);
        assertEquals(inventory.listProducts.get(0).getName(),"Teclado gamer");
    }
    @Test
    public void validateRegisterOrder() throws Exception {
        //Arrange
        setupStage3();
        //Act
        inventory.registerOrder("Alejandro",sdf.parse("2020-10-22"),35000,"Agua 500 ml//Camiseta Polo L//Marcador Sharpie".split("//"));
        //Assert
        assertEquals(inventory.listOrder.get(0).getUsername(),"Alejandro");
    }
    @Test
    public void validateThatInAnOrderProductDecreaseItsQuantityAvailable() throws Exception {
        //Arrange
        setupStage3();
        //Act
        inventory.registerOrder("Alejandro",sdf.parse("2020-10-22"),35000,"Agua 500 ml//Camiseta Polo L//Marcador Sharpie".split("//"));
        //Assert
        assertEquals(inventory.searchProductByName("Camiseta Polo L").getQuantityAvailable(),14);
        assertEquals(inventory.searchProductByName("Marcador Sharpie").getQuantityAvailable(),7);
        assertEquals(inventory.searchProductByName("Agua 500 ml").getQuantityAvailable(),14);
    }
    @Test
    public void validateOrderWithAnInexistingProduct() throws Exception {
        //Arrange
        setupStage4();
        //Act
        boolean result=false;
        try {
            inventory.registerOrder("Samuel", sdf.parse("2014-09-16"), 2660000, "PC Gamer//Soccer Ball".split("//"));
            result=true;
        }catch (ProductNotFoundException ex){
            ex.printStackTrace();
        }
        //Assert
        assertFalse(result);
        assertEquals(inventory.listProducts.size(),3);
    }
    @Test
    public void validateRemovingAnInexistingProduct(){
        //Arrange
        setupStage1();
        //Act
        boolean result =false;
        try {
            inventory.removeProduct("Leche");
            result =true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //Arrange
        assertFalse(result);
    }
    @Test
    public void removeAProduct() throws Exception {
        //Arrange
        setupStage2();
        //Act
        inventory.removeProduct("Hit mango Pet 500ML");
        //Assert
        assertEquals(inventory.listProducts.size(),1);
    }
    @Test
    public void increaseProductQuantity() throws Exception {
        //Arrange
        setupStage2();
        //Act
        inventory.increaseProductQuantity("Teclado gamer",20);
        //Arrange
        assertEquals(inventory.searchProductByName("Teclado gamer").getName(),"Teclado gamer");
    }
    @Test
    public void increaseNegativeQuantity() throws Exception{
        //Arrange
        setupStage2();
        //Act
        boolean result=false;
        try {
            inventory.increaseProductQuantity("Hit mango Pet 500ML",-5);
            result=true;
        }catch (IncreasingNegativeQuantityOfProductException ex){
            ex.printStackTrace();
        }
        //Assert
        assertFalse(result);
        assertEquals(inventory.searchProductByName("Hit mango Pet 500ML").getQuantityAvailable(),15);
    }
    @Test
    public void increasingQuantityOfAProductThatDoesNotExist() throws Exception {
        //Arrange
        setupStage2();
        //Act
        boolean result=false;
        try {
            inventory.increaseProductQuantity("Crema colgate",10);
            result =true;
        }catch (ProductNotFoundException ex){
            ex.printStackTrace();
        }
        //Assert
        assertFalse(result);
    }
    @Test
    public void validateSearchingProductByName() throws Exception {
        //Arrange
        setupStage2();
        //Act and Assert
        assertEquals(inventory.searchProductByName("Hit mango Pet 500ML").getNumberOfPurchases(),120);
    }
    @Test
    public void validateSearchingProductWithANameThatDoesnotExist() throws Exception {
        //Arrange
        setupStage2();
        //Act
        boolean result = false;
        try {
            inventory.searchProductByName("Chicharron dulce");
            result = true;
        }catch (ProductNotFoundException ex){
            ex.printStackTrace();
        }
        //Assert
        assertFalse(result);
    }
    @Test
    public void validateSearchingByProductPrice() throws Exception {
        //Arrange
        setupStage3();
        //Act and Assert
        assertEquals(inventory.searchProductPrize(2500),inventory.listProducts.get(0).toString()+"\n"+inventory.listProducts.get(1).toString()+"\n");
        //El anterior método me retorna un toString de los productos que coincidan con ese valor del precio, por eso el asser equals,
        //es get(0) y get(1), ya que primero me organiza el arraylist en orden ascendente para hacer luego la busqueda binaria.
    }
    @Test
    public void searchingWithANegativePrice() throws Exception {
        //Arrange
        setupStage3();
        //Act
        boolean result=false;
        try {
            inventory.searchProductPrize(-2000);
            result=true;
        }catch (ProductNotFoundException ex){
            ex.printStackTrace();
        }
        //Assert
        assertFalse(result);
    }
    @Test
    public void validateSearchByCategory() throws Exception {
        //Arrange
        setupStage3();
        //Act and Assert

        assertEquals(inventory.searchProductByCategory(ProductCategory.CLOTHING_ACCESSORIES),inventory.listProducts.get(0).toString()+"\n");
        //Son ordenados por categoria (alfabeticamente), (por la busqueda binaria)
        // En el setup Stage 3, CLOTHING_ACCESSORIES es la primera categoria que tiene productos en el escenario.
    }
    @Test
    public void validateSearchingProductsByNumberOfTimesPurchase() throws Exception {
        //Arrange
        setupStage3();
        //Act and Assert
        assertEquals(inventory.searchProductByNumberOfTimesPurchased(14), inventory.listProducts.get(1).toString()+"\n");
                                                                        //Cuando son ordenados de menor a mayor numero de ventas,
                                                                        //"Agua 500 ml", es la segunda en el stage
    }
    @Test
    public void validateSearchingOrderByName() throws Exception {
        //Arrange
        setupStage4();
        //Act and Assert
        assertEquals(inventory.searchOrderByUserName("Luna"),inventory.listOrder.get(0).toString()+"\n");
        //Como se ha dicho anteriormente, la búsqueda binaria me ordena el arreglo ascententemente.
        //Luna se encuentra en el pos 0, de ahi el get(0)
    }
    @Test
    public void validateSearchingOrderWithANameThatDoesNotExist() throws Exception {
        //Arrange
        setupStage4();
        //Act
        boolean result=false;
        try{
           inventory.searchOrderByUserName("Francisco");
           result=true;
        }catch(OrderNotFoundException ex){
            ex.printStackTrace();
        }
        //Assert
        assertFalse(result);
    }
    @Test
    public void validateSearchingOrderWithTotalPriceSale() throws Exception {
        //Arrange
        setupStage4();
        //Act and Assert
        assertEquals(inventory.searchOrderTotalPrice(25000),inventory.listOrder.get(0).toString()+"\n");
    }
    @Test
    public void validateSearchingOrderByDate() throws Exception {
        //Arrange
        setupStage4();
        //Act and Assert
        assertEquals(inventory.searchOrderByDate(sdf.parse("2023-04-02")), inventory.listOrder.get(0).toString()+"\n"+inventory.listOrder.get(1).toString()+"\n");
    }
}