package StoreOrderManagementSystem;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Admin {
    private String username = "group5";
    private String password = "cosc2081";

    public static void printProducts() throws FileNotFoundException {
        Product.listAllProduct();
    }

    public static void printOrders() throws FileNotFoundException{
        Order.listAllOrders();
    }

    public static void printMembers() throws FileNotFoundException {
        Customer.listAllMembers();
    }

    public static void addNewProduct() throws IOException {
        Product.addNewProduct();
    }

    public static void updatePrice() throws IOException {
        Product.updatePrice();
    }

    public static void memberGetOrderByID() throws FileNotFoundException {
        Order.getOrderByCustomerID();
    }

    public static void changeStatus() throws IOException {
        Order.changeStatus();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}