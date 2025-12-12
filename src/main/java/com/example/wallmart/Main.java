package com.example.walmart;

import com.example.walmart.model.Product;
import com.example.walmart.repo.ProductRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ProductRepository repo = new ProductRepository();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Walmart Simple App (console)");
        while (true) {
            System.out.println("\n1) List products\n2) Add product\n3) Exit\nChoose:");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": list(); break;
                case "2": add(); break;
                case "3": System.out.println("Bye"); return;
                default: System.out.println("Invalid"); break;
            }
        }
    }

    private static void list() {
        List<Product> all = repo.findAll();
        if (all.isEmpty()) {
            System.out.println("No products yet.");
            return;
        }
        all.forEach(System.out::println);
    }

    private static void add() {
        try {
            System.out.print("Name: "); String name = sc.nextLine().trim();
            System.out.print("Price: "); double price = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Quantity: "); int qty = Integer.parseInt(sc.nextLine().trim());
            Product p = new Product(0, name, price, qty);
            repo.save(p);
            System.out.println("Saved: " + p);
        } catch (Exception e) {
            System.out.println("Invalid input. Try again.");
        }
    }
}