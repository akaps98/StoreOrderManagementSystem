package StoreOrderManagementSystem;
import java.util.Scanner;
public class Admin {
    private String username = "group5";
    private String password = "cosc2081";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void Admin(String[] args) {
        new Admin();

        Scanner input = new Scanner(System.in);

        String username, password;

        System.out.print("Enter the username: ");
        username = input.nextLine();

        System.out.print("Enter the password: ");
        password = input.nextLine();

        if (username.equals("admingroup5") && (password.equals("cosc2081"))) {
            System.out.println("Welcome admin group 5.");
        } else {
            System.out.println("Wrong username or password!");
        }
    }
}

