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
            System.out.println("0. Logout");
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
                System.out.println("Successfully logged out!\n");
                ManagementSystem.orderManagementSystem();
                break;
            } else {
                System.out.println("Please enter the valid number.");
                System.out.println("---------------------------------------");
            }
        }
    }
}

