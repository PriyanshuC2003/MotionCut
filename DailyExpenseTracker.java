import java.io.*;
import java.util.*;

// Class to represent an expense
class Expense {
    private String category;
    private String description;
    private double amount;
    private String date;

    public Expense(String category, String description, double amount, String date) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String toString() {
        return date + " | " + category + " | " + amount + " | " + description;
    }

    public String toFileFormat() {
        return date + "," + category + "," + amount + "," + description;
    }

    public String getDate() {
        return date;
    }
    
    public double getAmount() {
        return amount;
    }
}

// Class to manage expenses
class ExpenseManager {
    private List<Expense> expenses;
    private static final String FILE_NAME = "expenses.txt";

    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpensesFromFile();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpensesToFile();
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    public void viewSummary() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        System.out.println("Total Expenses: $" + total);
    }

    private void saveExpensesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.println(e.toFileFormat());
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses.");
        }
    }

    private void loadExpensesFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                expenses.add(new Expense(data[1], data[3], Double.parseDouble(data[2]), data[0]));
            }
        } catch (Exception e) {
            System.out.println("Error loading expenses.");
        }
    }
}

// Main class to run the application
public class DailyExpenseTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        while (true) {
            System.out.println("\n1. Add Expense\n2. View Expenses\n3. View Summary\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    manager.addExpense(new Expense(category, description, amount, date));
                    System.out.println("Expense added!");
                    break;
                case 2:
                    manager.viewExpenses();
                    break;
                case 3:
                    manager.viewSummary();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            scanner.close();
        }
    }
}
