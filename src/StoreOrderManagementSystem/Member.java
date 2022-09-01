package StoreOrderManagementSystem;

import java.io.*;
import java.util.*;

public class Member {

    // member system
    public static void memberSystem() throws IOException {
        Scanner input = new Scanner(System.in);

        while(true) {
            int option;
            System.out.println("Welcome to the member system.");
            System.out.println("Enter the number for the wanted function.");
            System.out.println("---------------------------------------");
            System.out.println("1. List all the products (no order)");
            System.out.println("2. Search product");
            System.out.println("3. List all the products by price");
            System.out.println("4. Create a new order");
            System.out.println("5. Get information of an order by using order ID");
            System.out.println("0. Terminate system");
            System.out.println("---------------------------------------");

            option = input.nextInt();

            if(option == 1) {
                Product.listAllProduct();
            } else if (option == 2){
                Category.displayProductByCategory();
            } else if (option == 3){
                Product.sortByPrice();
            } else if (option == 4){
                // create a new order
            } else if (option == 5){
                // get information of an order by using order ID
            } else if (option == 0){
                System.out.println("Thank you for using our order system!");
                break;
            } else {
                System.out.println("Please enter the valid number.");
                System.out.println("---------------------------------------");
            }
        }
    }


    // membership update
    private String spending;
    private String membership;

    public void updateMembership() throws IOException {
        Scanner before = new Scanner(new File("member.db"));
        ArrayList<String> after = new ArrayList<>();
        String newMembership;
        double discount;

        while (before.hasNext()) {
            String line = before.nextLine();
            String[] eachInfo = line.split(",");  // 1
            spending = eachInfo[2];
            membership = eachInfo[3];

            int totalSpending = Integer.parseInt(spending);
            if((totalSpending > 5000000) && (totalSpending < 10000000)) {
                discount = 0.05;
                newMembership = "silver";
                List<String> upgrade = Arrays.asList(eachInfo);
                upgrade.set(3, newMembership);
                after.add(String.join(",", upgrade));
            } else if((totalSpending > 10000000) && (totalSpending < 250000000)) {
                discount = 0.10;
                newMembership = "gold";
                List<String> upgrade = Arrays.asList(eachInfo);
                upgrade.set(3, newMembership);
                after.add(String.join(",", upgrade));
            } else if(totalSpending > 250000000) {
                discount = 0.15;
                newMembership = "platinum";
                List<String> upgrade = Arrays.asList(eachInfo);
                upgrade.set(3, newMembership);
                after.add(String.join(",", upgrade));
            } else {
                discount = 0;
                newMembership = "regular";
                List<String> downgrade = Arrays.asList(eachInfo);
                downgrade.set(3, newMembership);
                after.add(String.join(",", downgrade));
            }
        }
        before.close();

        PrintWriter update = new PrintWriter(new FileWriter("member.db", false));
        for (String line : after) {
            update.println(line);
        }
        update.close();


        // test code for updating membership (1)
        try {
            String filePath = "member.db";
            FileInputStream fileStream = null;

            fileStream = new FileInputStream(filePath);
            byte[ ] readBuffer = new byte[fileStream.available()];
            while (fileStream.read(readBuffer) != -1){
            }
            System.out.println(new String(readBuffer));

            fileStream.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    // test code for updating membership (2)
    public static void main(String[] args) throws IOException {
        Member test = new Member();
        test.updateMembership();
    }
}

