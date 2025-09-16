package com.project.three.demo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.*;

public class Files {

    private static final Path APP_SUPPORT_DIR = Path.of(System.getProperty("user.home"), "Library", "Application Support", "MustafaCart");

    private static final String USER_FILE = "UserData.txt";
    private static final String CATALOGUE_FILE = "Catalogue.txt";

    static {
        try {
            if (!java.nio.file.Files.exists(APP_SUPPORT_DIR)) {
                java.nio.file.Files.createDirectories(APP_SUPPORT_DIR);
            }
            copyIfMissing(USER_FILE);
            copyIfMissing(CATALOGUE_FILE);
        } catch (IOException e) {
            System.err.println("Error preparing application data directory: " + e.getMessage());
        }
    }

    /**
     * Method to transfer files from app if is first launch
     * @param fileName : String
     */
    private static void copyIfMissing(String fileName) {
        Path dest = APP_SUPPORT_DIR.resolve(fileName);
        if (!java.nio.file.Files.exists(dest)) {
            try (InputStream in = Files.class.getResourceAsStream(
                    "/com/project/three/demo/files/" + fileName)) {
                if (in == null) {
                    System.err.println("Default resource not found: " + fileName);
                    return;
                }
                java.nio.file.Files.copy(in, dest);
                System.out.println("Copied default " + fileName + " to " + dest);
            } catch (IOException e) {
                System.err.println("Error copying default file " + fileName + ": " + e.getMessage());
            }
        }
    }
    /**
     * Loader method reading catalogue from file into data structure
     * @param catalogue : ArrayList<Item>
     */
    public static void loadCatalogue(ArrayList<Item> catalogue) {
        Path filePath = APP_SUPPORT_DIR.resolve(CATALOGUE_FILE);
        try (Scanner input = new Scanner(filePath)) {
            while (input.hasNextLine()) {
                String itemName = input.nextLine();
                String itemDescription = input.nextLine();
                double itemPrice = Double.parseDouble(input.nextLine().trim());
                double discount = Double.parseDouble(input.nextLine().trim());
                catalogue.add(new Item(itemName, itemDescription, itemPrice, discount));
            }
        } catch (Exception e) {
            System.out.println("Error loading Catalogue! " + e.getMessage());
        }
    }
    /**
     * Loader method reading user info from file to data structure upon program launch
     * @param systemAccounts ArrayList<Account>
     */
    public static void loadUsers(ArrayList<Account> systemAccounts) {
        Path filePath = APP_SUPPORT_DIR.resolve(USER_FILE);
        try (Scanner inFS = new Scanner(filePath)) {
            inFS.nextLine();
            while (inFS.hasNext()) {
                boolean isAdmin = Boolean.parseBoolean(inFS.nextLine().trim());
                int customerId = Integer.parseInt(inFS.nextLine().trim());
                String password = inFS.nextLine().trim();
                String securityQuestion = inFS.nextLine().trim();
                String securityAnswer = inFS.nextLine().trim();
                if (!isAdmin) {
                    String cardHolder = inFS.nextLine().trim();
                    String address = inFS.nextLine().trim();
                    String cardNumber = inFS.nextLine().trim();
                    double balance = Double.parseDouble(inFS.nextLine().trim());
                    ArrayList<Integer> authNumber = new ArrayList<>();
                    int count = Integer.parseInt(inFS.nextLine().trim());
                    for (int i = 0; i < count; i++) {
                        authNumber.add(Integer.parseInt(inFS.nextLine().trim()));
                    }
                    BankAccount userAccount = new BankAccount(cardHolder, address, cardNumber, balance, authNumber);
                    ArrayList<Order> orders = new ArrayList<>();
                    count = Integer.parseInt(inFS.nextLine().trim());
                    for (int i = 0; i < count; i++) {
                        int orderID = Integer.parseInt(inFS.nextLine().trim());
                        boolean deliveryType = Boolean.parseBoolean(inFS.nextLine().trim());
                        ArrayList<Item> items = new ArrayList<>();
                        int count2 = Integer.parseInt(inFS.nextLine().trim());
                        String orderDate = inFS.nextLine();
                        for (int j = 0; j < count2; j++) {
                            String itemName = inFS.nextLine().trim();
                            String itemDescription = inFS.nextLine().trim();
                            double itemPrice = Double.parseDouble(inFS.nextLine().trim());
                            double discount = Double.parseDouble(inFS.nextLine().trim());
                            int quantity = Integer.parseInt(inFS.nextLine().trim());
                            items.add(new Item(itemName, itemDescription, itemPrice, discount, quantity));
                        }
                        orders.add(new Order(orderID, deliveryType, orderDate, items));
                    }
                    systemAccounts.add(new Account(isAdmin, customerId, password, securityQuestion, securityAnswer, userAccount, orders));
                } else {
                    systemAccounts.add(new Account(isAdmin, customerId, password, securityQuestion, securityAnswer, null, null));
                }
                String temp = inFS.nextLine().trim();
                if (temp.equals("EOF")) {
                    System.out.println("Successfully loaded information!");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading UserData! " + e.getMessage());
        }
    }
    /**
     * Storage Method saving user info from data structure to file before program termination
     * @param systemAccounts : ArrayList<Account>
     */
    public static void saveUsers(ArrayList<Account> systemAccounts) {
        Path filePath = APP_SUPPORT_DIR.resolve(USER_FILE);
        try (PrintWriter outFS = new PrintWriter(filePath.toFile())) {
            for (Account account : systemAccounts) {
                outFS.print(account.toFile());
            }
            outFS.print("EOF");
            System.out.println("Successfully saved information!");
        } catch (Exception e) {
            System.out.println("Error saving Users! " + e.getMessage());
        }
    }
}
