package StoreOrderManagementSystem;
import java.io.*;
import java.util.*;

public class Order {
    private String id;
    private Customer customer;
    private ArrayList<Product> productsList;
    private String status;

    public Order() {};

    public Order(String id, Customer customer, ArrayList<Product> productsList, String status) {
        this.id = id;
        this.customer = customer;
        this.productsList = productsList;
        this.status = status;
    }

    public int productSum() {
        int total = 0;
        for (Product p : productsList) {
            total += p.getProductPrice();
        }
        return total;
    }

    public static void addIntoOrder() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Scanner input = new Scanner(new File("product.db"));
        ArrayList<Product> listOfProduct = new ArrayList<>();
        ArrayList<Product> orderList = new ArrayList<>();
        String productName;
        while (input.hasNext()) {
            String[] line = input.nextLine().split(",");
            Product p1 = new Product(line[0], line[1], Integer.parseInt(line[2]), line[3]);
            listOfProduct.add(p1);
        }
        while (true) {
            boolean invalid = false;
            System.out.println("Please enter product's name: ");
            productName = scanner.nextLine();
            if (Product.checkExistProduct(productName)) {
                for (Product p : listOfProduct) {
                    if (productName.equalsIgnoreCase(p.getProductName())) {
                        orderList.add(p);
                    }
                }

            } else {
                System.out.println("Product does not exist! Please enter a valid product name.");
                invalid = true;
            }
            if (invalid) continue;
            break;
        }
        System.out.println("Product added successfully!");
        for (Product p2 : orderList) {
            Product.printProduct(p2);
        }
    }
}
