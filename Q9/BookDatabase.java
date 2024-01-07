package ninth_test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookDatabase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Book> bookDatabase = new HashMap<>();

        // Accept user input to add books
        System.out.print("Enter the number of books to add: ");
        int numBooks = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numBooks; i++) {
            System.out.println("Enter details for book " + (i + 1) + ":");
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Author: ");
            String author = scanner.nextLine();
            System.out.print("Publisher: ");
            String publisher = scanner.nextLine();
            System.out.print("Price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            Book book = new Book(title, author, publisher, price);
            bookDatabase.put(book.getBookId(), book);
        }

        // Display all books
        System.out.println("\nAll Books:");
        for (Book book : bookDatabase.values()) {
            System.out.println(book);
        }

        // Find books by title
        System.out.print("\nEnter a title or part of a title to search for: ");
        String searchTitle = scanner.nextLine().toLowerCase();
        System.out.println("Matching Books:");
        for (Book book : bookDatabase.values()) {
            if (book.getTitle().toLowerCase().contains(searchTitle)) {
                System.out.println(book);
            }
        }

        // Identify books by a publisher
        System.out.print("\nEnter a publisher to identify books: ");
        String searchPublisher = scanner.nextLine();
        System.out.println("Books from Publisher '" + searchPublisher + "':");
        for (Book book : bookDatabase.values()) {
            if (book.getPublisher().equalsIgnoreCase(searchPublisher)) {
                System.out.println(book);
            }
        }

        // Update publisher details based on title
        System.out.print("\nEnter the title of the book to update its publisher: ");
        String updateTitle = scanner.nextLine();
        System.out.print("Enter the new publisher: ");
        String newPublisher = scanner.nextLine();

        for (Book book : bookDatabase.values()) {
            if (book.getTitle().equalsIgnoreCase(updateTitle)) {
                book.setPublisher(newPublisher);
                System.out.println("Publisher details updated for the book with title '" + updateTitle + "'.");
                break;
            }
        }

        // Display all books after updates
        System.out.println("\nUpdated Books:");
        for (Book book : bookDatabase.values()) {
            System.out.println(book);
        }
    }
}
