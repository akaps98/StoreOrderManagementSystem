import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        welcomeScreen();
        orderManagementSystem();
    }

    public static void orderManagementSystem() {
        Scanner input = new Scanner(System.in);
        while(true) {
            int checkPerson;
            System.out.println("Welcome to the order management system.");
            System.out.println("If you are customer, enter 1.");
            System.out.println("If you are administrator, enter 2.");

            checkPerson = input.nextInt();

            if(checkPerson == 1) { // if he/she is customer,

            } else { // if he/she is admin,

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
        System.out.println("s3914412, Nguyen Duong Truong Thinh");
    }
}
