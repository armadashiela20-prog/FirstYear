/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package princehypermartreceiptv2;

import javax.swing.JOptionPane;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;

public class PrinceHypermartReceiptV2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
         String correctPIN = "062006";
        String inputPIN = JOptionPane.showInputDialog("Enter Cashier PIN:");

        if (inputPIN == null || !correctPIN.equals(inputPIN)) {
            JOptionPane.showMessageDialog(null, "Incorrect PIN! Program closing.");
            System.exit(0);
        }
        
        // Store Information
              String greetings =                 "WELCOME";
              String storeName =  "   >>>       P R I N C E      <<< ";
              String address =     " Tankulan, Manolo Fortich, Bukidnon ";
              String storeTIN =            "VAT REG TIN: 441-790-582-004";       // FEATURE 6
              String storeContact =       "Contact: 0909-575-1260"; // FEATURE 6
        // Receipt No
        int receiptNo = (int) (Math.random() * 900000 + 100000);

        // Customer & Cashier Info
        String customer = JOptionPane.showInputDialog("Enter customer name:");
        String cashier = JOptionPane.showInputDialog("Enter cashier name:");

        // Number of Items
        String countInput = JOptionPane.showInputDialog("How many items do you want to add?");
        int itemCount = Integer.parseInt(countInput);

        // Arrays
        String[] itemNames = new String[100]; // Increased size to handle potential adding logic if needed
        double[] prices = new double[100];
        int[] qty = new int[100];
        String[] categories = new String[100]; // FEATURE 1

        // Category options
        String[] categoryOptions = {"Grocery", "Snacks", "Drinks", "Meat", "Frozen", "Household", "Other"};

        // ==================== ITEM INPUT ====================
        for (int i = 0; i < itemCount; i++) {
            itemNames[i] = JOptionPane.showInputDialog("Enter name of item " + (i + 1) + ":");
            categories[i] = (String) JOptionPane.showInputDialog(
                    null,
                    "Select category for " + itemNames[i],
                    "Item Category",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    categoryOptions,
                    categoryOptions[0]
            );
            prices[i] = Double.parseDouble(JOptionPane.showInputDialog("Enter price of " + itemNames[i] + ":"));
            qty[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity of " + itemNames[i] + ":"));
        }

        // ==================== FEATURE 3: EDIT OR REMOVE ITEMS ====================
        int editChoice = JOptionPane.showConfirmDialog(null,
                "Do you want to EDIT or REMOVE items before printing?",
                "Edit Items",
                JOptionPane.YES_NO_OPTION);

        if (editChoice == JOptionPane.YES_OPTION) {
            boolean editing = true;

            while (editing) {
                StringBuilder list = new StringBuilder("Current Items:\n");
                for (int i = 0; i < itemCount; i++) {
                    list.append((i + 1)).append(". ").append(itemNames[i])
                        .append(" - ").append(categories[i])
                        .append(" (Qty: ").append(qty[i]).append(")\n");
                }

                String choice = JOptionPane.showInputDialog(list + "\nEnter item number to modify/remove (or 0 to finish):");

                // Handle cancel or empty input
                if (choice == null || choice.isEmpty()) {
                    editing = false;
                    break;
                }

                int index = Integer.parseInt(choice) - 1;

                if (index == -1) { // user entered 0
                    editing = false;
                    break;
                }

                if (index >= 0 && index < itemCount) {
                    String[] actions = {"Edit Item", "Remove Item"};
                    int actionChoice = JOptionPane.showOptionDialog(null, "Choose action for " + itemNames[index],
                            "Modify Item", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, actions, actions[0]);

                    if (actionChoice == 0) { // Edit Item
                        itemNames[index] = JOptionPane.showInputDialog("New name:", itemNames[index]);
                        categories[index] = (String) JOptionPane.showInputDialog(
                                null, "Select category: ", "Item Category",
                                JOptionPane.QUESTION_MESSAGE, null, categoryOptions, categories[index]);
                        prices[index] = Double.parseDouble(JOptionPane.showInputDialog("New price:", prices[index]));
                        qty[index] = Integer.parseInt(JOptionPane.showInputDialog("New quantity:", qty[index]));

                    } else if (actionChoice == 1) { // Remove Item
                        
                        // --- THIS IS THE FIX FOR THE REMOVE LOGIC ---
                        for (int j = index; j < itemCount - 1; j++) {
                            itemNames[j] = itemNames[j + 1];
                            prices[j] = prices[j + 1];         // Shift Price
                            qty[j] = qty[j + 1];               // Shift Qty
                            categories[j] = categories[j + 1]; // Shift Category
                        }
                        itemCount--; // Decrease the total count
                        JOptionPane.showMessageDialog(null, "Item removed.");
                        // ---------------------------------------------
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid item number.");
                }
            }
        }

        // ==================== CALCULATE TOTALS ====================
        double grandTotal = 0.0;
        for (int i = 0; i < itemCount; i++) {
            grandTotal += (prices[i] * qty[i]);
        }

        // ==================== PAYMENT ====================
        double payment = 0.0;
        boolean validPayment = false;
        
        while (!validPayment) {
            String payInput = JOptionPane.showInputDialog("Total Amount: " + grandTotal + "\nEnter Payment Amount:");
            if (payInput == null) System.exit(0); // Exit if canceled
            
            payment = Double.parseDouble(payInput);
            
            if (payment >= grandTotal) {
                validPayment = true;
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient payment! You need " + (grandTotal - payment) + " more.");
            }
        }
        
        double change = payment - grandTotal;

        // ==================== GENERATE RECEIPT ====================
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        StringBuilder receipt = new StringBuilder();
        receipt.append("===============================================\n");
        // *** GREETINGS ADDED HERE ***
        receipt.append("                                            WELCOME \n");
        receipt.append("").append("\n                      ");
        receipt.append( "   >>>      P R I N C E  H Y P E R M A R T     <<< ").append("\n                         ");
        receipt.append(address).append("\n                                   ");
        receipt.append(storeContact).append("\n                              ");
        receipt.append(storeTIN).append("\n");
        receipt.append("=================================================\n  ");
        receipt.append("Date: ").append(dtf.format(now)).append("\n  ");
        receipt.append("Receipt #: ").append(receiptNo).append("\n  ");
        receipt.append("Cashier: ").append(cashier).append("\n ");
        receipt.append("Customer: ").append(customer).append("\n   ");
        receipt.append("---------------------------------------------------------\n  ");
        receipt.append(String.format("%-15s %-10s %-10s %-10s\n  ", "Item", "Qty", "Price", "Total"));
        
        for (int i = 0; i < itemCount; i++) {
            double lineTotal = prices[i] * qty[i];
            receipt.append(String.format("%-15s %-5d %-10.2f %-10.2f\n", 
                itemNames[i], qty[i], prices[i], lineTotal));
        }
        
        receipt.append("------------------------------------------\n");
        receipt.append("  Total Items: ").append(itemCount).append("\n");
        receipt.append("  GRAND TOTAL: ").append(grandTotal).append("\n");
        receipt.append("  CASH:        ").append(payment).append("\n");
        receipt.append("  CHANGE:      ").append(change).append("\n");
        receipt.append("----------------------------------------- \n");
        receipt.append("                   ****   THANK YOU FOR SHOPPING!    ****      \n");
        receipt.append("============================================");

        // Display Receipt
        JOptionPane.showMessageDialog(null, new javax.swing.JTextArea(receipt.toString()));

        // ==================== SAVE TO FILE ====================
        try {
            FileWriter writer = new FileWriter("Receipt_" + receiptNo + ".txt");
            writer.write(receipt.toString());
            writer.close();
            JOptionPane.showMessageDialog(null, "Receipt saved to Receipt_" + receiptNo + ".txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving receipt.");
        }
    }
}
