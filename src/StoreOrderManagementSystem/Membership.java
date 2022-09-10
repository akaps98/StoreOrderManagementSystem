package StoreOrderManagementSystem;

import java.io.*;
import java.util.*;

public class Membership {
    public static void updateMembership() throws IOException {
        Scanner before = new Scanner(new File("member.db"));
        ArrayList<String> after = new ArrayList<>();
        String newMembership, spending;

        while (before.hasNext()) {
            String line = before.nextLine();
            String[] eachInfo = line.split(",");  // 1
            spending = eachInfo[2];

            int totalSpending = Integer.parseInt(spending);
            if((totalSpending > 5000000) && (totalSpending < 10000000)) {
                newMembership = "silver";
                List<String> upgrade = Arrays.asList(eachInfo);
                upgrade.set(3, newMembership);
                after.add(String.join(",", upgrade));
            } else if((totalSpending > 10000000) && (totalSpending < 25000000)) {
                newMembership = "gold";
                List<String> upgrade = Arrays.asList(eachInfo);
                upgrade.set(3, newMembership);
                after.add(String.join(",", upgrade));
            } else if(totalSpending > 25000000) {
                newMembership = "platinum";
                List<String> upgrade = Arrays.asList(eachInfo);
                upgrade.set(3, newMembership);
                after.add(String.join(",", upgrade));
            } else {
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
    }
}
