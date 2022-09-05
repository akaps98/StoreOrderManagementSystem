package StoreOrderManagementSystem;

import java.io.*;
import java.util.*;

public class Membership {
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
            } else if((totalSpending > 10000000) && (totalSpending < 25000000)) {
                discount = 0.10;
                newMembership = "gold";
                List<String> upgrade = Arrays.asList(eachInfo);
                upgrade.set(3, newMembership);
                after.add(String.join(",", upgrade));
            } else if(totalSpending > 25000000) {
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
        Membership test = new Membership();
        test.updateMembership();
    }
}
