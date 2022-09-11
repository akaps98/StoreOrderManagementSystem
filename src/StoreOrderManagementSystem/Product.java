package StoreOrderManagementSystem;

import java.io.*;
import java.util.*;

public class Product {
    private String productID;
    private String productName;
    private int productPrice;
    private String category;

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
           }
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
                System.out.println("Product already exists!");
                invalid = true;
            }
            if (invalid) continue;
            break;}

        System.out.println("Please enter product's price: ");
        newPrice = Integer.parseInt(scanner.nextLine());

        while (true) {
            boolean invalid = false;
            System.out.println("Please enter product's category: ");
            newCategory = scanner.nextLine();
            String[] array = newCategory.split(" ");
            for (String s: array){
                if (!s.matches("[\\w]+")){
                    System.out.println("Please enter a valid product's category!\n");
                    invalid = true;
                }
            }
            if (invalid) continue;
            System.out.println("New product has been added!\n");
            break;
        }

        PrintWriter output = new PrintWriter(new FileWriter("product.db", true));
        output.println(newID + "," + newName + "," + newPrice + "," + newCategory);
        output.close();
    }

    public static void updatePrice() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter product that you want to update price: ");
        String update = scanner.nextLine();
        int count = 0;
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
                count += 1;
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
        if (count == 1) {
            System.out.println("Update completed!\n");
        } else {
            System.out.println("There is no such product!\n");
        }
    }

    public static void removeProduct() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter product's name that you want to remove: ");
        String remove = scanner.nextLine();
        Scanner input = new Scanner(new File("product.db"));
        ArrayList<String> newFile = new ArrayList<>();
        int count = 0;
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] lineName = line.split(",");
            if (!remove.equalsIgnoreCase(lineName[1])) {
                newFile.add(line);
            } else {
                count += 1;
            }
        }
        input.close();

        PrintWriter output = new PrintWriter(new FileWriter("product.db", false));
        for (String line:newFile) {
            output.println(line);
        }
        output.close();
        if (count == 1) {
            System.out.println("Remove completed!\n");
        } else {
            System.out.println("The products list will remain the same\n");
        }

    }

    public static Product findProductByName(String name) throws FileNotFoundException {
        Scanner input = new Scanner(new File("product.db"));
        Product product = null;
        while (input.hasNext()) {
            String[] line = input.nextLine().split(",");
            if (name.equalsIgnoreCase(line[1])) {
                product = new Product(line[0], line[1], Integer.parseInt(line[2]), line[3]);
            }
        }
        return product;
    }

    public static void listAllProduct() throws FileNotFoundException {
        Scanner input = new Scanner(new File("product.db"));
        while (input.hasNext()) {
            String[] line = input.nextLine().split(",");
            Product product = new Product(line[0], line[1], Integer.parseInt(line[2]), line[3]);
            printProduct(product);
        }
        input.close();
    }

    public static void printProduct(Product product) {
        System.out.println("Product ID: " + product.getProductID() + '\n' +
                "Product Name: " + product.getProductName() + '\n' +
                "Product Price: " + product.getProductPrice() + " VND" + '\n' +
                "Category: " + product.getCategory() + '\n' +
                "--------------------------------------------------");
    }

    public static void sortByPrice() throws FileNotFoundException {
        ArrayList<Product> sort= new ArrayList<>();
        Scanner input = new Scanner(new File("product.db"));
        while (input.hasNext()) {
            String[] line = input.nextLine().split(",");
            Product product = new Product(line[0], line[1], Integer.parseInt(line[2]), line[3]);
            sort.add(product);
        }
        input.close();
        Comparator<Product> compareByPrice = new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
//                return Integer.toString(o1.getProductPrice()).compareTo(Integer.toString(o2.getProductPrice())) ;
                return Integer.compare(o1.getProductPrice(), o2.getProductPrice());
            }
        };

        Scanner scanner = new Scanner(System.in);
        while (true) {
            boolean invalid = false;
            System.out.println("Please enter 1 to sort from low to high and 2 to sort from high to low: ");
            String sortInt = scanner.nextLine();
            if (!sortInt.matches("[1-2]") ) {
                System.out.println("Please enter 1 or 2!");
                invalid = true;
            }
            if (invalid) continue;
            if (sortInt.equals("1")) {
                sort.sort(compareByPrice);
            } else {
                sort.sort(compareByPrice.reversed());
            }
            break;
        }

        for (Product product:sort) {
            printProduct(product);
        }
    }

    public String getProductID() {
        return productID;
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
