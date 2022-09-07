package StoreOrderManagementSystem;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Customer {
    private String ID;
    private String fullname;
    private String phoneNumber;
    private int spending;
    private String membership;
    private String username;
    private String password;

    public Customer() {}

    public Customer(String ID, String fullname, String phoneNumber, int spending, String membership, String username, String password) {
        this.ID = ID;
        this.fullname = fullname;
        this.spending = spending;
        this.membership = membership;
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

        String ID, fullname, phoneNumber, username, password;
        int spending = 0;
        String membership = "regular";

        if(!(memberList.isEmpty())) {
            String previousMember = memberList.get(memberList.size() - 1).split(",")[0];
            StringBuilder e = new StringBuilder(previousMember);
            e.deleteCharAt(0);
            e.deleteCharAt(0);
            Integer s = Integer.parseInt(String.valueOf(e));
            s += 1;
            ID = "C%s".formatted(s);
        } else {
            ID = "C1";
        }

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
                if(phoneNumber.matches(eachInfo[4])) {
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
                if(username.toLowerCase(Locale.ROOT).matches(eachInfo[5].toLowerCase(Locale.ROOT))) {
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
            System.out.println("Type your password(without any space & special letters are allowed.): ");
            password = input.nextLine();
            if(Pattern.matches(mat, password)) {
                break;
            }
            System.out.println("Invalid format. Try again");
            System.out.println("-------------------------");
        }

        PrintWriter writeDB = new PrintWriter(new FileWriter("member.db", true));
        writeDB.printf("%s,%s,%d,%s,%s,%s,%s%n", ID, fullname, spending, membership, phoneNumber, username, password);
        writeDB.close();

        System.out.println("Congratulation! Registration has been done!");
        System.out.println("---------------------------------------");
    }

    public static Customer signIn() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        Scanner file = new Scanner(new File("member.db"));
        ArrayList<String> memberList = new ArrayList<>();

        while(file.hasNext()) {
            List<String> sen = Arrays.asList(file.nextLine().split("%n"));
            memberList.add(String.valueOf(sen));
        }

        String inputUsername, inputPassword;
        String ID = "";
        String spending = "";
        String membership = "";
        String realPassword = "";
        String fullname = "";
        String phoneNumber = "";

        System.out.println("Type username: ");

        while(true) {
            inputUsername = input.nextLine();

            boolean checkUsername = false;

            for (String info : memberList) {
                String[] eachInfo = info.split(",");
                if (inputUsername.toLowerCase(Locale.ROOT).matches(eachInfo[5].toLowerCase(Locale.ROOT))) {
                    realPassword = eachInfo[6];
                    realPassword = realPassword.substring(0, realPassword.length() - 1);
                    ID = eachInfo[0];
                    ID = ID.substring(1);
                    fullname = eachInfo[1];
                    spending = eachInfo[2];
                    membership = eachInfo[3];
                    phoneNumber = eachInfo[4];
                    inputUsername = eachInfo[5];
                    checkUsername = true;
                    break;
                }
            }

            if (checkUsername) {
                break;
            } else {
                System.out.println("The username is not exists.");
                System.out.println("-------------------------");
                System.out.println("If you want to return main screen, press 1.");
                System.out.println("If you want to retype username, press 2.");
                int forgetUsername = input.nextInt();
                if(forgetUsername == 1) {
                    return null;
                } else if(forgetUsername == 2) {
                    System.out.println("Type username: ");
                    inputPassword = input.nextLine();
                    continue;
                } else {
                    System.out.println("Please enter the valid number.");
                }
            }
        }

        System.out.println("Type password: ");

        while(true) {
            inputPassword = input.nextLine();
            if(inputPassword.equals(realPassword)) {
                System.out.println("Successfully logged in!\n");
                break;
            } else {
                System.out.println("Wrong password.");
                System.out.println("-------------------------");
                System.out.println("If you want to return main screen, press 1.");
                System.out.println("If you want to retype password, press 2.");
                int forgetPassword = input.nextInt();
                if(forgetPassword == 1) {
                    return null;
                } else if(forgetPassword == 2) {
                    System.out.println("Type password: ");
                    inputPassword = input.nextLine();
                    continue;
                } else {
                    System.out.println("Please enter the valid number.");
                }
            }
        }

        int totalSpending = Integer.parseInt(spending);

        Customer customer = new Customer(ID, fullname, phoneNumber, totalSpending, membership, inputUsername, realPassword);

        return customer;
    }

    public static void listProfile(Customer member) {
        System.out.printf("%s's User Profile%n", member.getFullname());
        System.out.println("-------------------------");
        System.out.printf("ID: %s%nName: %s%nPhone Number: %s%nMembership: %s%nTotal spending: %s%n", member.getID(), member.getFullname(), member.getPhoneNumber(), member.getMembership(), member.getSpending());
        System.out.println("-------------------------");
        System.out.printf("Username: %s%nPassword: %s%n", member.getUsername(), member.getPassword());
        System.out.println("-------------------------");
    }

    public static void listAllProduct() throws FileNotFoundException {
        Product.listAllProduct();
    }

    public static void searchProductsForCategory() throws FileNotFoundException {
        Category.displayProductByCategory();
    }

    public static void sortByPrice() throws FileNotFoundException {
        Product.sortByPrice();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public int getSpending() {
        return spending;
    }

    public void setSpending(int spending) {
        this.spending = spending;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }
}