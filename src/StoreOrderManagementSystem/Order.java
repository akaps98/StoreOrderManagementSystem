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
            Scanner file = new Scanner(new File("order.db"));
            ArrayList<String> orderList = new ArrayList<>();
            String orderId = UUID.randomUUID().toString();

            while(file.hasNext()) {
                List<String> sen = Arrays.asList(file.nextLine().split("%n"));
                orderList.add(String.valueOf(sen));
            }

            if(!(orderList.isEmpty())) {
                String previousOrder = orderList.get(orderList.size() - 1).split(",")[0];
                StringBuilder e = new StringBuilder(previousOrder);
                e.deleteCharAt(0);
                e.deleteCharAt(0);
                int s = Integer.parseInt(String.valueOf(e));
                s += 1;
                orderId = "O%s".formatted(s);
            } else {
                orderId = "O1";
            }

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
                } else {
                    System.out.println("Please enter y or n");
                }
            }

            if (!orderProduct.isEmpty()) {
                ArrayList<String> productName = new ArrayList<>();
                for (Product p: orderProduct) {
                    productName.add(p.getProductName());
                }
                String listString = String.join("-", productName);
                PrintWriter output = new PrintWriter(new FileWriter("order.db", true));
                String line = orderId + ","  +  customer + "," + listString + "," + orderStatus;
                output.println(line);
                output.close();
                System.out.println("You have successfully placed your order!");
            } else {
                System.out.println("Goodbye");
            }

        }

    public static boolean checkExistCustomer(String customer) throws FileNotFoundException {
        Scanner input = new Scanner(new File("member.db"));
        ArrayList<String> exist = new ArrayList<>();
        int count = 0;
        while (input.hasNext()) {
            String[] line = input.nextLine().split(",");
            exist.add(line[0]);
        }
        for (String i: exist) {
            if (customer.equalsIgnoreCase(i)) {
                count++;
            } ;
        }
        input.close();
        return count != 0;
    }

        public static void getOrderByCustomerID() throws FileNotFoundException {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the customer's ID: ");
            String customerID = scanner.nextLine();
            int count = 0;
            Scanner input = new Scanner(new File("order.db"));

            ArrayList<Order> orderList = new ArrayList<>();
            if (!checkExistCustomer(customerID)) {
                System.out.println("This customer does not exist.");
            } else {
                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    String[] lineInfo = line.split(",");
                    String[] productName = lineInfo[2].split("-");
                    if (customerID.equalsIgnoreCase(lineInfo[1])) {
                        ArrayList<Product> productList = new ArrayList<>();
                        for (String s: productName) {
                            productList.add(Product.findProductByName(s));
                        }
                        orderList.add(new Order(lineInfo[0], lineInfo[1], productList, lineInfo[3]));
                    }
                }
                if (orderList.size() == 0) {
                    System.out.println("This customer has no order");
                } else {
                    for (Order o: orderList) {
                        Order.printOrder(o);
                    }
                }
            }
        }

        public static String printProductOfOrder(Order order) {
        ArrayList<String> stringList = new ArrayList<>();
        for (Product product: order.getProductsList()) {
            stringList.add(
                    "Product ID: " + product.getProductID() + '\n' +
                            "Product Name: " + product.getProductName() + '\n' +
                            "Product Price: " + product.getProductPrice() + " VND" + '\n' +
                            "Category: " + product.getCategory() + '\n' +
                            "-------------------------"
            );
        }
            return String.join("\n", stringList);
        }
    public static void printOrder(Order order) {
        System.out.println("Order ID: " + order.getId() + '\n' +
                "Customer ID: " + order.getCustomer()+ '\n' +
                "Product List: "  + '\n' +
                "-------------------------"
                + '\n' + Order.printProductOfOrder(order) + '\n' +
                "Status: " + order.getStatus() + '\n' +
                "--------------------------------------------------");
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(ArrayList<Product> productsList) {
        this.productsList = productsList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

