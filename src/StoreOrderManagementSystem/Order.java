package StoreOrderManagementSystem;
import java.io.*;
import java.util.*;

public class Order {
    private String id;
    private String customer;
    private ArrayList<Product> productsList;
    private String status;

    public Order() {};

    public Order(String id, String customer, ArrayList<Product> productsList, String status) {
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

    public static Product addIntoOrder() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Scanner input = new Scanner(new File("product.db"));
        ArrayList<Product> listOfProduct = new ArrayList<>();
        String productName;
        Product newProduct = null;
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
                        newProduct = p;
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
        return newProduct;
        }

        public static void createNewOrder(String customer) throws IOException {
            String orderId = UUID.randomUUID().toString();
            ArrayList<Product> orderProduct = new ArrayList<>();
            String orderStatus = "UNPAID";
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Please enter y to continue order or n to finish ordering process");
                String key = scanner.nextLine();
                if (key.equalsIgnoreCase("y")) {
                    Product newProduct = Order.addIntoOrder();
                    orderProduct.add(newProduct);
                }
                else if (key.equalsIgnoreCase("n")) {
                    break;
                }
            }
            for (Product p: orderProduct) {
                Product.printProduct(p);
            }
            PrintWriter output = new PrintWriter(new FileWriter("order.db", true));
            String line = orderId + "," +  customer + "," + orderStatus;
            output.close();
        }
    }

