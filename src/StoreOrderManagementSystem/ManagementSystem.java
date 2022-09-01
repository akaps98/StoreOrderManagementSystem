package StoreOrderManagementSystem;

import java.io.IOException;
import java.util.Scanner;

public class ManagementSystem {
    public static void orderManagementSystem() throws IOException {
        welcomeScreen();
        Scanner input = new Scanner(System.in);
        while(true) {
            int option;
            System.out.println("Welcome to the order management system.");
            System.out.println("Enter the number for the wanted function.");
            System.out.println("---------------------------------------");
            System.out.println("1. Register");
            System.out.println("2. Sign In");
            System.out.println("3. List all the products (no order)");
            System.out.println("4. Search product");
            System.out.println("5. List all the products by price");
            System.out.println("0. Terminate system");
            System.out.println("---------------------------------------");

            option = input.nextInt();

            if(option == 1) {
                Customer.registration();
            } else if (option == 2){
                String username = Customer.signIn();
//                if(username == adminUsername) {
//                    admin function (features 8 ~ 12)
//                } else {
//                    member function (features 3 ~ 7)
//                }
            } else if (option == 3){
                // list all the products
                Product.listAllProduct();
            } else if (option == 4){
                // searching
                Category.displayProductByCategory();
            } else if (option == 5){
                // list all the products by price
                Product.sortByPrice();
            } else if (option == 0){
                System.out.println("Thank you for using our order system!");
                break;
            } else {
                System.out.println("Please enter the valid number.");
                System.out.println("---------------------------------------");
            }
        }
    }

    public static void welcomeScreen() {
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("STORE ORDER MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu ");
        System.out.println("Group 5");
        System.out.println("s3916884, Junsik Kang");
        System.out.println("s3926977, Doan Thien Di");
        System.out.println("s3864235, Seungmin Lee");
        System.out.println("s3914412, Nguyen Duong Truong Thinh\n");
    }
}
