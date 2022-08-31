package StoreOrderManagementSystem;

import java.io.*;
import java.util.*;

public class Product {
    private String productID;
    private String productName;
    private int productPrice;
    private String category;

    public Product() {}

    public Product(String productID, String productName, int productPrice, String category) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.category = category;
    }

    public static boolean checkExistProduct(String product) throws FileNotFoundException {
        Scanner input = new Scanner(new File("product.db"));
        ArrayList<String> exist = new ArrayList<>();
        int count = 0;
        while (input.hasNext()) {
            String[] line = input.nextLine().split(",");
            exist.add(line[1]);
        }
        for (String i: exist) {
           if (product.equalsIgnoreCase(i)) {
               count++;
           } ;
        }
        input.close();
        return count != 0;
    }

    public static void addNewProduct() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String newID = UUID.randomUUID().toString();
        String newName;
        int newPrice;
        String newCategory;
        while (true) {
            boolean invalid = false;
            System.out.println("Please enter product's name: ");
            newName = scanner.nextLine();
            String[] array = newName.split(" ");
            for (String s: array){
                    if (!s.matches("[\\w]+")){
                        System.out.println("Please enter a valid product's name!");
                        invalid = true;
                }
            }
            if (checkExistProduct(newName)) {
                System.out.println("StoreOrderManagementSystem.Product already exists!");
                invalid = true;
            }
            if (invalid) continue;
            break;}

        System.out.println("Please enter product's price: ");
        newPrice = Integer.parseInt(scanner.nextLine());
                ;
        while (true) {
            boolean invalid2 = false;
            System.out.println("Please enter product's category: ");
            newCategory = scanner.nextLine();
            String[] array = newCategory.split(" ");
            for (String s: array){
                    if (!s.matches("[\\w]")){
                        System.out.println("Please enter a valid product's category!");
                        invalid2 = true;
                }
            }
            if (invalid2) continue;
            break;}

        PrintWriter output = new PrintWriter(new FileWriter("product.db", true));
        output.println(newID + "," + newName + "," + newPrice + "," + newCategory);
        output.close();
    }

    public static void updatePrice() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter product that you want to update price: ");
        String update = scanner.nextLine();
        Scanner input = new Scanner(new File("product.db"));
        ArrayList<String> newFile = new ArrayList<>();
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] lineInfo = line.split(",");
            if (update.equalsIgnoreCase(lineInfo[1])) {
                System.out.println("Current price is " + lineInfo[2] + " VND");
                Scanner priceScanner = new Scanner(System.in);
                System.out.println("Please enter the price you want to update: ");
                String newPrice = priceScanner.nextLine();
                List<String> modify = Arrays.asList(lineInfo);
                modify.set(2, newPrice);
                newFile.add(String.join(",", modify));
            } else {
                newFile.add(line);
            }
        }
        input.close();

        PrintWriter output = new PrintWriter(new FileWriter("product.db", false));
        for (String line:newFile) {
            output.println(line);
        }
        output.close();
        System.out.println("Update completed!");
    };

    public static void removeProduct() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter product that you want to remove: ");
        String remove = scanner.nextLine();
        Scanner input = new Scanner(new File("product.db"));
        ArrayList<String> newFile = new ArrayList<>();
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] lineName = line.split(",");
            if (!remove.equalsIgnoreCase(lineName[1])) {
                newFile.add(line);
            }
        }
        input.close();

        PrintWriter output = new PrintWriter(new FileWriter("product.db", false));
        for (String line:newFile) {
            output.println(line);
        }
        output.close();
        System.out.println("Remove completed!");
    }
    public static void listAllProduct() throws FileNotFoundException {
        Scanner input = new Scanner(new File("product.db"));
        while (input.hasNext()) {
            String[] line = input.nextLine().split(",");
            Product product = new Product(line[0], line[1], Integer.parseInt(line[2]), line[3]);
            System.out.print("StoreOrderManagementSystem.Product ID: " + product.getProductID() + '\n' +
                    "StoreOrderManagementSystem.Product Name: " + product.getProductName() + '\n' +
                    "StoreOrderManagementSystem.Product Price: " + product.getProductPrice() + " VND" + '\n' +
                    "Category: " + product.getCategory() + '\n' +
                    "--------------------------------------------------" + '\n');
        }
        input.close();
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }


    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
