package StoreOrderManagementSystem;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Customer {
    private String ID;
    private String fullname;
    private String phoneNumber;
    private String membership;
    private String username;
    private String password;

    public Customer() {}

    public Customer(String ID, String fullname, String phoneNumber, String membership, String username, String password) {
        this.ID = ID;
        this.fullname = fullname;
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
        String membership = "regular";

        if(!(memberList.isEmpty())) {
            int memberNum = Integer.parseInt(memberList.get(memberList.size() - 1).substring(5, 6)) + 1;
            ID = "C000%s".formatted(memberNum);
        } else {
            ID = "C0001";
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
                if(phoneNumber.matches(eachInfo[3])) {
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
                if(username.toLowerCase(Locale.ROOT).matches(eachInfo[4].toLowerCase(Locale.ROOT))) {
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
        writeDB.printf("%s,%s,%s,%s,%s,%s%n", ID, fullname, membership, phoneNumber, username, password);
        writeDB.close();

        System.out.println("Congratulation! Registration has been done!");
        System.out.println("---------------------------------------");
    }

    public static String signIn() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        Scanner file = new Scanner(new File("member.db"));
        ArrayList<String> memberList = new ArrayList<>();

        while(file.hasNext()) {
            List<String> sen = Arrays.asList(file.nextLine().split("%n"));
            memberList.add(String.valueOf(sen));
        }

        String inputUsername, inputPassword;
        String ID = "";
        String membership = "";
        String realPassword = "";
        String fullname = "";
        String phoneNumber = "";

        while(true) {
            System.out.println("Type username: ");
            inputUsername = input.nextLine();

            boolean checkUsername = false;

            for (String info : memberList) {
                String[] eachInfo = info.split(",");
                if (inputUsername.toLowerCase(Locale.ROOT).matches(eachInfo[4].toLowerCase(Locale.ROOT))) {
                    realPassword = eachInfo[5];
                    realPassword = realPassword.substring(0, realPassword.length() - 1);
                    ID = eachInfo[0];
                    ID = ID.substring(1);
                    fullname = eachInfo[1];
                    membership = eachInfo[2];
                    phoneNumber = eachInfo[3];
                    inputUsername = eachInfo[4];
                    checkUsername = true;
                    break;
                }
            }

            if (checkUsername) {
                break;
            } else {
                System.out.println("The username is not exists.");
                System.out.println("-------------------------");
            }
        }

        while(true) {
            System.out.println("Type password: ");
            inputPassword = input.nextLine();
            if(inputPassword.matches(realPassword)) {
                System.out.println("Successfully logged in!\n");
                break;
            } else {
                System.out.println("Wrong password.");
                System.out.println("-------------------------");
            }
        }

        System.out.printf("%s's User Profile%n", fullname);
        System.out.println("-------------------------");
        System.out.printf("ID: %s%nName: %s%nMembership: %s%nPhone Number: %s%nusername: %s%npassword: %s%n", ID, fullname, membership, phoneNumber, inputUsername, realPassword);
        System.out.println("-------------------------");

        return inputUsername;
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