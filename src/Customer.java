import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

public class Customer {
    private String fullname;
    private String phoneNumber;
    private String username;
    private String password;

    public Customer() {}

    public Customer(String fullname, String phoneNumber, String username, String password) {
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public static void registration() throws IOException {
        Scanner input = new Scanner(System.in);

        Scanner file = new Scanner(new File("member.db"));
        ArrayList<String> memberList = new ArrayList<>();

        while(file.hasNext()) {
            List<String> sen = Arrays.asList(file.nextLine().split("%n"));
            memberList.add(String.valueOf(sen));
        }

        String fullname, phoneNumber, username, password;

        while(true) {
            String mat = "^[a-zA-Z\\s]*$";
            System.out.println("Type your full name: ");
            fullname = input.nextLine();
            if(Pattern.matches(mat, fullname)) {
                break;
            }
            System.out.println("Invalid format. Try again");
            System.out.println("-------------------------");
        }

        while(true) {
            String mat = "^[0-9]*$";
            int check = 0;

            System.out.println("Type your phone number(without any space): ");
            phoneNumber = input.nextLine();

            if(!(Pattern.matches(mat, phoneNumber))) {
                check = 1;
            }

            for(String info : memberList) {
                String[] eachInfo = info.split(",");
                if(phoneNumber.matches(eachInfo[1])) {
                    check = 2;
                }
            }

            if(check == 0) { // no conflicts
                break;
            } else if(check == 1) { // invalid format
                System.out.println("Invalid format. Try again");
                System.out.println("-------------------------");
            } else { // already exists
                System.out.println("This phone number already exists. Please type another phone number.");
                System.out.println("-------------------------");
            }
        }

        while(true) {
            String mat = "^[a-zA-Z\\d]*$";
            int check = 0;

            System.out.println("Type your username(without any space & only English letter and numbers): ");
            username = input.nextLine();

            if(!(Pattern.matches(mat, username))) {
                check = 1;
            }

            for(String info : memberList) {
                String[] eachInfo = info.split(",");
                if(username.toLowerCase(Locale.ROOT).matches(eachInfo[2].toLowerCase(Locale.ROOT))) {
                    check = 2;
                }
            }

            if(check == 0) { // no conflicts
                break;
            } else if(check == 1) { // invalid format
                System.out.println("Invalid format. Try again");
                System.out.println("-------------------------");
            } else { // already exists
                System.out.println("This username already exists. Please type another username.");
                System.out.println("-------------------------");
            }
        }

        while(true) {
            String mat = "^[a-zA-Z\\d`~!@#$%^&*()-_=+]*$";
            System.out.println("Type your password(without any space): ");
            password = input.nextLine();
            if(Pattern.matches(mat, password)) {
                break;
            }
            System.out.println("Invalid format. Try again");
            System.out.println("-------------------------");
        }

        Customer newRegister = new Customer(fullname, phoneNumber, username, password);

        PrintWriter writeDB = new PrintWriter(new FileWriter("member.db", true));
        writeDB.printf("%s,%s,%s,%s%n", fullname, phoneNumber, username, password);
        writeDB.close();

        System.out.println("Congratulation! Registration has been done!");
    }



    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}