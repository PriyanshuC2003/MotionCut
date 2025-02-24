import java.util.Scanner;

public class LinkShortenerCLI {
    public static void main(String[] args) {
        URLShortener shortener = new URLShortener();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Shorten URL\n2. Expand URL\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter long URL: ");
                String longURL = scanner.nextLine();
                System.out.println("Short URL: " + shortener.shortenURL(longURL));
            } else if (choice == 2) {
                System.out.print("Enter short URL: ");
                String shortURL = scanner.nextLine();
                System.out.println("Original URL: " + shortener.expandURL(shortURL));
            } else {
                break;
            }
        }

        scanner.close();
    }
}
