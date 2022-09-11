package StoreOrderManagementSystem;

import java.io.IOException;
import java.util.Scanner;

public class ManagementSystem {
    public static void orderManagementSystem() throws IOException {
        welcomeScreen();
        Scanner input = new Scanner(System.in);
        label:
        while (true) {
            String option;
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

            option = input.nextLine();

            switch (option) {
                case "1":
                    Customer.registration();
                    break;
                case "2":
                    Customer member = Customer.signIn();
                    if (member == null) {
                        System.out.println("---------------------------------------");
                    } else if (member.getUsername().equals("group5")) { // "group5" is username for admin account.
                        label1:
                        while (true) { // admin function (features 3 ~ 7)
                            String adminOption;
                            System.out.println("This page is for an administrator functions.");
                            System.out.println("Enter the number for the wanted function.");
                            System.out.println("---------------------------------------");
                            System.out.println("1. List all the products");
                            System.out.println("2. List all the orders");
                            System.out.println("3. List all the members");
                            System.out.println("4. Add a new product to the store");
                            System.out.println("5. Remove product");
                            System.out.println("6. Update price of product");
                            System.out.println("7. Get information of all orders by Customer ID");
                            System.out.println("8. Change the status of the order");
                            System.out.println("9. Today's report");
                            System.out.println("0. Logout");
                            System.out.println("---------------------------------------");

                            adminOption = input.nextLine();

                            switch (adminOption) {
                                case "1":
                                    Admin.printProducts();
                                    break;
                                case "2":
                                    Admin.printOrders();
                                    break;
                                case "3":
                                    Admin.printMembers();
                                    break;
                                case "4":
                                    Admin.addNewProduct();
                                    break;
                                case "5":
                                    Admin.removeProduct();
                                    break;
                                case "6":
                                    Admin.updatePrice();
                                    break;
                                case "7":
                                    Admin.memberGetOrderByID();
                                    break;
                                case "8":
                                    Admin.changeStatus();
                                    break;
                                case "9":
                                    Admin.reportOfTheDay();
                                    break;
                                case "0":
                                    System.out.println("Successfully logged out!");
                                    System.out.println("Return to the main screen...");
                                    System.out.println("---------------------------------------");
                                    break label1;
                                default:
                                    System.out.println("Please enter the valid number.");
                                    System.out.println("---------------------------------------");
                                    break;
                            }
                        }
                    } else {
                        label1:
                        while (true) { // member function (features 3 ~ 7)
                            String memberOption;
                            System.out.printf("Welcome, %s!%n", member.getFullname());
                            System.out.println("Enter the number for the wanted function.");
                            System.out.println("---------------------------------------");
                            System.out.println("1. Check the user information");
                            System.out.println("2. List all the products (no order)");
                            System.out.println("3. Search product");
                            System.out.println("4. List all the products by price");
                            System.out.println("5. Create a new order");
                            System.out.println("6. Get information of an order by using order ID");
                            System.out.println("0. Logout");
                            System.out.println("---------------------------------------");

                            memberOption = input.nextLine();

                            switch (memberOption) {
                                case "1":
                                    Customer.listProfile(member);
                                    break;
                                case "2":
                                    Customer.listAllProduct();
                                    break;
                                case "3":
                                    Customer.searchProductsForCategory();
                                    break;
                                case "4":
                                    Customer.sortByPrice();
                                    break;
                                case "5":
                                    Customer.placeOrder(member.getID());
                                    break;
                                case "6":
                                    Customer.memberGetOrderByID(member.getID());
                                    break;
                                case "0":
                                    System.out.println("Successfully logged out!");
                                    System.out.println("Return to the main screen...");
                                    System.out.println("---------------------------------------");
                                    break label1;
                                default:
                                    System.out.println("Please enter the valid number.");
                                    System.out.println("---------------------------------------");
                                    break;
                            }
                        }
                    }
                    break;
                case "3":
                    Customer.listAllProduct();
                    break;
                case "4":
                    Customer.searchProductsForCategory();
                    break;
                case "5":
                    Customer.sortByPrice();
                    break;
                case "0":
                    System.out.println("Thank you for using our order system!");
                    break label;
                default:
                    System.out.println("Please enter the valid number.");
                    System.out.println("---------------------------------------");
                    break;
            }
        }
    }

    public static void welcomeScreen() {
        System.out.println(":::::::::::::::::::::::::::::::::::::::");
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("STORE ORDER MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu ");
        System.out.println("Group 5");
        System.out.println("s3916884, Junsik Kang");
        System.out.println("s3926977, Doan Thien Di");
        System.out.println("s3864235, Seungmin Lee");
        System.out.println("s3914412, Nguyen Duong Truong Thinh");
        System.out.println(":::::::::::::::::::::::::::::::::::::::");
    }
}
