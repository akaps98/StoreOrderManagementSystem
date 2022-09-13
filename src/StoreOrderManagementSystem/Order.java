package StoreOrderManagementSystem;

import java.io.*;
import java.time.*;
import java.util.*;

public class Order {
    private String id;
    private String customer;
    private ArrayList<Product> productsList;
    private String status;
    private int totalPrice;

    private LocalDate date;

    public Order() {

    }

    public Order(String id, String customer, ArrayList<Product> productsList, String status, int totalPrice, LocalDate date) {
        this.id = id;
        this.customer = customer;
        this.productsList = productsList;
        this.status = status;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public static int productSum(ArrayList<Product> productsList) {
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

    public static void createNewOrder(String customerID) throws IOException {
        Scanner file = new Scanner(new File("order.db"));
        ArrayList<String> orderList = new ArrayList<>();
        String orderId;

        while(file.hasNext()) {
            List<String> sen = Arrays.asList(file.nextLine().split("%n"));
            orderList.add(String.valueOf(sen));
        }

        String membership = "";

        Scanner members = new Scanner(new File("member.db"));

        while (members.hasNext()) {
            String[] line = members.nextLine().split(",");
            if(line[0].equals(customerID)) {
                membership = line[3];
                break;
            }
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

        int totalCost = Order.productSum(orderProduct);

        switch (membership) {
            case "silver" -> totalCost *= 0.95;
            case "gold" -> totalCost *= 0.9;
            case "platinum" -> totalCost *= 0.85;
        }

        Order order = new Order(orderId, customerID, orderProduct, orderStatus, totalCost, LocalDate.now());

        if (!orderProduct.isEmpty()) {
            ArrayList<String> productName = new ArrayList<>();
            for (Product p: orderProduct) {
                productName.add(p.getProductName());
            }
            String listString = String.join("-", productName);
            PrintWriter output = new PrintWriter(new FileWriter("order.db", true));
            String line = orderId + ","  +  customerID + "," + listString + "," + orderStatus + "," + order.getTotalPrice() + "," + LocalDate.now();
            output.println(line);
            output.close();
            System.out.println("You have successfully placed your order!");
            System.out.println("Your order ID is " + orderId + "!");
            System.out.println("Total cost is " + totalCost + "!\n");
        } else {
            System.out.println("You've ordered nothing.\n");
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
            }
        }
        input.close();
        return count != 0;
    }

    public static void getOrderByCustomerID() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the customer's ID: ");
        String customerID = scanner.nextLine();
        Scanner input = new Scanner(new File("order.db"));
        ArrayList<Order> orderList = new ArrayList<>();

        if (!checkExistCustomer(customerID)) {
            System.out.println("This customer does not exist.\n");
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
                    orderList.add(new Order(lineInfo[0], lineInfo[1], productList, lineInfo[3], Integer.parseInt(lineInfo[4]), LocalDate.parse(lineInfo[5])));
                }
            }
            if (orderList.size() == 0) {
                System.out.println("This customer has no order.\n");
            } else {
                for (Order o: orderList) {
                    Order.printOrder(o);
                }
            }
        }
    }

    public static void memberGetOrderByID(String customerID) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Scanner input = new Scanner(new File("order.db"));
        int count = 0;
        System.out.println("Please enter the order's ID: ");
        String orderID = scanner.nextLine();

        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] lineInfo = line.split(",");
            String[] productName = lineInfo[2].split("-");
            if (customerID.equalsIgnoreCase(lineInfo[1]) && orderID.equalsIgnoreCase(lineInfo[0])) {
                ArrayList<Product> productList = new ArrayList<>();
                for (String s: productName) {
                    productList.add(Product.findProductByName(s));
                }
                count += 1;
                Order.printOrder(new Order(lineInfo[0], lineInfo[1], productList, lineInfo[3], Integer.parseInt(lineInfo[4]), LocalDate.parse(lineInfo[5])));
            }
        }
        if (count == 0) {
            System.out.println("There is no order with this ID or This order is not yours.\n");
        }
    }

    public static void changeStatus() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter orderID that you want to change status: ");
        String update = scanner.nextLine();
        int count = 0;
        int count2 = 0;
        boolean rewriteMember = false;
        Scanner input = new Scanner(new File("order.db"));
        Scanner members = new Scanner(new File("member.db"));
        ArrayList<String> newFile = new ArrayList<>();
        ArrayList<String> updatemember = new ArrayList<>();

        while (input.hasNext()) {
            String line = input.nextLine();
            String[] lineInfo = line.split(",");
            String customerID, membership;
            int cost, spending;
            if (update.equalsIgnoreCase(lineInfo[0])) {
                System.out.println("Current status is " + lineInfo[3] + ".");
                Scanner statusScanner = new Scanner(System.in);
                if (lineInfo[3].equalsIgnoreCase("PAID")) {
                    newFile.add(line);
                    count2 += 1;
                    System.out.println("You cannot change the status of PAID orders.\n");
                } else {
                    while (true) {
                        System.out.println("Please enter y if you want to update the status to PAID or n to cancel: ");
                        String newStatus = statusScanner.nextLine();
                        if (newStatus.equalsIgnoreCase("y"))   {
                            List<String> modify = Arrays.asList(lineInfo);
                            modify.set(3, "PAID");
                            customerID = lineInfo[1];
                            cost = Integer.parseInt(lineInfo[4]);
                            while(members.hasNext()) {
                                String memberLine = members.nextLine();
                                String[] memberInfo = memberLine.split(",");
                                if(memberInfo[0].equals(customerID)) {
                                    List<String> updateSpending = Arrays.asList(memberInfo);
                                    spending = Integer.parseInt(memberInfo[2]);
                                    spending += cost;
                                    String newSpendingValue = Integer.toString(spending);
                                    updateSpending.set(2, newSpendingValue);
                                    updatemember.add(String.join(",", updateSpending));
                                    rewriteMember = true;
                                    continue;
                                }
                                updatemember.add(memberLine);
                            }
                            newFile.add(String.join(",", modify));
                            count += 1;
                            break;
                        } else if (newStatus.equalsIgnoreCase("n")) {
                            newFile.add(line);
                            count2 += 1;
                            System.out.println("This order's status will remain UNPAID.\n");
                            break;
                        } else {
                            System.out.println("Please enter y or n.\n");
                        }
                    }
                }
            } else {
                newFile.add(line);
            }
        }
        input.close();

        PrintWriter output = new PrintWriter(new FileWriter("order.db", false));
        for (String line : newFile) {
            output.println(line);
        }
        output.close();

        if(rewriteMember) {
            PrintWriter memberOutput = new PrintWriter(new FileWriter("member.db", false));
            for (String line : updatemember) {
                memberOutput.println(line);
            }
            memberOutput.close();
        }

        if (count == 1) {
            System.out.println("Update completed!\n");
        } else if (count2 != 1) {
            System.out.println("There is no such order.\n");
        }
    }

    public static void totalRevenueOfTheDay() throws FileNotFoundException {
        String today = LocalDate.now().toString();
        Scanner input = new Scanner(new File("order.db"));
        ArrayList<Order> newFile = new ArrayList<>();
        int totalRevenue = 0;

        System.out.println("Today(" + today + ")'s report");
        System.out.println("#########################");

        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] lineInfo = line.split(",");
            String[] productName = lineInfo[2].split("-");
            if (today.equalsIgnoreCase(lineInfo[5])) {
                ArrayList<Product> productList = new ArrayList<>();
                for (String s: productName) {
                    productList.add(Product.findProductByName(s));
            }
                newFile.add(new Order(lineInfo[0], lineInfo[1], productList, lineInfo[3], Integer.parseInt(lineInfo[4]), LocalDate.parse(lineInfo[5])));
            }
        }

        for (Order o: newFile) {
            totalRevenue += o.getTotalPrice();
            printOrder(o);
        }
        System.out.println("Today's total ordered revenue(" + today + "): " + totalRevenue + " VND\n");
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
                "Product List: " + '\n' +
                "-------------------------" + '\n' +
                "-------------------------" + '\n' +
                printProductOfOrder(order)  + '\n' +
                "-------------------------"
                + '\n' +
                "Status: " + order.getStatus() + '\n' +
                "Total price: " + order.getTotalPrice() + " VND" + '\n' +
                "Date of order: " + order.getDate() + '\n' +
                "--------------------------------------------------");
    }

    public static void listAllOrders() throws FileNotFoundException{
        Scanner input = new Scanner(new File("Order.db"));
        while (input.hasNext()) {
            int idx = 0;
            String[] line = input.nextLine().split(",");
            List<String> productList = Arrays.asList(line[2].split("-"));
            System.out.println("Order ID: " + line[0]);
            System.out.println("Customer ID: " + line[1]);
            System.out.print("Ordered Products: ");
            for(String product : productList) {
                if(idx == productList.size() - 1) {
                    System.out.printf("%s", product);
                    break;
                }
                System.out.printf("%s, ", product);
                idx++;
            }
            System.out.println("");
            System.out.println("Status: " + line[3]);
            System.out.println("Total Price: " + line[4]);
            System.out.println("Date of Order: " + line[5]);
            System.out.println("--------------------------------------------------");
        }
        input.close();
    }

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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

