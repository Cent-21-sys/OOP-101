import java.util.Scanner;

public class Canteen {

    public static void main(String[] args) {
        String[] itemNames = {
            "Fried Rice",
            "Chicken Sandwich",
            "Spaghetti",
            "Iced Tea",
            "Burger with Fries"
        };
        double[] itemPrices = {
            50.00,
            80.00,
            65.00,
            25.00,
            95.00
        };

        Scanner sc = new Scanner(System.in);

        int totalQuantity = 0;
        double totalBeforeDeductions = 0.0;
        double totalDeduction = 0.0;

        displayMenu(itemNames, itemPrices);

        String orderAgain = "Y";

        while (orderAgain.equalsIgnoreCase("Y")) {
            System.out.println("\n--- New Order ---");

            System.out.print("Enter item number (1-" + itemNames.length + "): ");
            int itemNumber;
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Item number must be a whole number.");
                sc.next();
                orderAgain = askOrderAgain(sc);
                continue;
            }
            itemNumber = sc.nextInt();

            System.out.print("Enter quantity (1-10): ");
            int quantity;
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Quantity must be a whole number.");
                sc.next();
                orderAgain = askOrderAgain(sc);
                continue;
            }
            quantity = sc.nextInt();

            System.out.print("Are you a student? (Y/N): ");
            String studentInput = sc.next().trim();

            boolean validItem = itemNumber >= 1 && itemNumber <= itemNames.length;
            boolean validQuantity = quantity >= 1 && quantity <= 10;
            boolean validStudentInput = studentInput.equalsIgnoreCase("Y") || studentInput.equalsIgnoreCase("N");

            if (!validItem || !validQuantity || !validStudentInput) {
                System.out.println("\n*** INVALID ORDER ***");
                if (!validItem) {
                    System.out.println("- Item number must be between 1 and " + itemNames.length + ".");
                }
                if (!validQuantity) {
                    System.out.println("- Quantity must be at least 1 and no more than 10.");
                }
                if (!validStudentInput) {
                    System.out.println("- Student status must be Y or N.");
                }
                System.out.println("This order will NOT be included in the purchase computation.");
                orderAgain = askOrderAgain(sc);
                continue;
            }

            boolean isStudent = studentInput.equalsIgnoreCase("Y");
            String chosenItem = itemNames[itemNumber - 1];
            double chosenPrice = itemPrices[itemNumber - 1];
            double orderAmount = chosenPrice * quantity;

            double deductionRate = 0.0;
            String deductionDescription = "No deduction applicable";
            boolean qualifiesBulk = orderAmount >= 500.0;

            if (isStudent && qualifiesBulk) {
                deductionRate = 0.15;
                deductionDescription = "Student + Purchase >= $500 (15% deduction)";
            } else if (isStudent) {
                deductionRate = 0.10;
                deductionDescription = "Student discount (10% deduction)";
            } else if (qualifiesBulk) {
                deductionRate = 0.05;
                deductionDescription = "Bulk purchase >= $500 (5% deduction)";
            }

            double deductionAmount = orderAmount * deductionRate;
            double amountToPay = orderAmount - deductionAmount;

            System.out.println("\n--- Order Summary ---");
            System.out.println("Item: " + chosenItem);
            System.out.printf("Price per item: $%.2f%n", chosenPrice);
            System.out.println("Quantity: " + quantity);
            System.out.printf("Amount before deduction: $%.2f%n", orderAmount);
            System.out.println("Deduction applied: " + deductionDescription);
            System.out.printf("Deduction amount: $%.2f%n", deductionAmount);
            System.out.printf("Amount to pay for this order: $%.2f%n", amountToPay);

            totalQuantity += quantity;
            totalBeforeDeductions += orderAmount;
            totalDeduction += deductionAmount;

            orderAgain = askOrderAgain(sc);
        }

        double finalAmountToPay = totalBeforeDeductions - totalDeduction;

        System.out.println("\n============================================");
        System.out.println("          FINAL TRANSACTION SUMMARY");
        System.out.println("============================================");
        System.out.println("Total quantity of items purchased : " + totalQuantity);
        System.out.printf("Total amount before deductions     : $%.2f%n", totalBeforeDeductions);
        System.out.printf("Total deduction                    : $%.2f%n", totalDeduction);
        System.out.printf("Final amount to pay                : $%.2f%n", finalAmountToPay);
        System.out.println("============================================");
        System.out.println("Thank you for ordering. Goodbye!");

        sc.close();
    }

    private static void displayMenu(String[] itemNames, double[] itemPrices) {
        System.out.println("============================================");
        System.out.println("           CANTEEN MENU");
        System.out.println("============================================");
        System.out.printf("%-4s %-25s %-10s%n", "No.", "Item", "Price");
        System.out.println("--------------------------------------------");
        for (int i = 0; i < itemNames.length; i++) {
            System.out.printf("%-4d %-25s $%-9.2f%n", (i + 1), itemNames[i], itemPrices[i]);
        }
        System.out.println("============================================");
    }

    private static String askOrderAgain(Scanner sc) {
        String answer;
        while (true) {
            System.out.print("\nDo you want to order again? (Y/N): ");
            answer = sc.next().trim();
            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N")) {
                return answer;
            }
            System.out.println("Please enter only Y or N.");
        }
    }
}