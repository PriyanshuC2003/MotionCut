import java.io.*;
import java.util.*;

class Expense {
    String date, category;
    double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return date + " | " + category + " | $" + amount;
    }
}

class User {
    String username, password;
    List<Expense> expenses = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";
    private static Map<String, User> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        loadExpenses();
        while (true) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> registerUser();
                case 2 -> loginUser();
                case 3 -> {
                    saveExpenses();
                    System.exit(0);
                }
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("User already exists!");
        } else {
            users.put(username, new User(username, password));
            System.out.println("Registration successful!");
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            currentUser = users.get(username);
            System.out.println("Login successful!");
            userMenu();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("1. Add Expense\n2. View Expenses\n3. Category Total\n4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewExpenses();
                case 3 -> categoryTotal();
                case 4 -> {
                    currentUser = null;
                    return;
                }
            }
        }
    }

    private static void addExpense() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        currentUser.expenses.add(new Expense(date, category, amount));
        System.out.println("Expense added!");
    }

    private static void viewExpenses() {
        if (currentUser.expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("Date | Category | Amount");
            for (Expense e : currentUser.expenses) {
                System.out.println(e);
            }
        }
    }

    private static void categoryTotal() {
        Map<String, Double> totals = new HashMap<>();
        for (Expense e : currentUser.expenses) {
            totals.put(e.category, totals.getOrDefault(e.category, 0.0) + e.amount);
        }
        System.out.println("Category Totals:");
        totals.forEach((category, total) -> System.out.println(category + ": $" + total));
    }

    private static void loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            users = (Map<String, User>) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }

    private static void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }
}
