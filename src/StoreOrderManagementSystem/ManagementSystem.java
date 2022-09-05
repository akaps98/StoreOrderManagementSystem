package StoreOrderManagementSystem;

import java.io.IOException;
import java.util.Scanner;

public class ManagementSystem {
    public static void orderManagementSystem() throws IOException {
        welcomeScreen();
        Scanner input = new Scanner(System.in);
        while (true) {
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

            if (option == 1) {
                Customer.registration();
            } else if (option == 2) {
                Customer member = Customer.signIn();
//                if(member == null) {
//                    System.out.println("---------------------------------------");
//                    continue;
//                } else if(member.getUsername() == Admin.getUsername()) {
//                    //admin function (features 8 ~ 12)
//                } else {
//                    while (true) { // member function (features 3 ~ 7)
//                        int memberOption;
//                        System.out.printf("Welcome, %s!%n", member.getFullname());
//                        System.out.println("Enter the number for the wanted function.");
//                        System.out.println("---------------------------------------");
//                        System.out.println("1. Check the user information");
//                        System.out.println("2. List all the products (no order)");
//                        System.out.println("3. Search product");
//                        System.out.println("4. List all the products by price");
//                        System.out.println("5. Create a new order");
//                        System.out.println("6. Get information of an order by using order ID");
//                        System.out.println("0. Logout");
//                        System.out.println("---------------------------------------");
//
//                        memberOption = input.nextInt();
//
//                        if (memberOption == 1) {
//                            Customer.listProfile(member);
//                        } else if (memberOption == 2) {
//                            Customer.listAllProduct();
//                        } else if (memberOption == 3) {
//                            Customer.searchProductsForCategory();
//                        } else if (memberOption == 4) {
//                            Customer.sortByPrice();
//                        } else if (memberOption == 5) {
//                            // create a new order
//                        } else if (memberOption == 6) {
//                            // get information of an order by using order ID
//                        } else if (memberOption == 0 ) {
//                            System.out.println("Successfully logged out!");
//                            System.out.println("Return to the main screen...");
//                            System.out.println("---------------------------------------");
//                            break;
//                        } else {
//                            System.out.println("Please enter the valid number.");
//                            System.out.println("---------------------------------------");
//                        }
//                    }
//                }
            } else if (option == 3) {
                Customer.listAllProduct();
            } else if (option == 4) {
                Customer.searchProductsForCategory();
            } else if (option == 5) {
                Customer.sortByPrice();
            } else if (option == 0) {
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
