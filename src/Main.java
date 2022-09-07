import StoreOrderManagementSystem.Customer;
import StoreOrderManagementSystem.ManagementSystem;
import StoreOrderManagementSystem.Order;
import StoreOrderManagementSystem.Product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//        ManagementSystem.orderManagementSystem();
        ArrayList<Product> proList = new ArrayList<>();
        proList.add(new Product("dsdfs", "Laptop", 9867243, "Laptop"));
        proList.add(new Product("dfsrgr", "Laptop Dell", 2897333, "Laptop"));
        Order newOrder = new Order("O2", "Di", proList, "UNPAID");
        Order.printOrder(newOrder);
    }
}