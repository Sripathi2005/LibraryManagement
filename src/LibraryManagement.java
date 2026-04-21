import java.util.*;
import java.io.*;

public class LibraryManagement {

    static final String FILE_NAME = "books.dat";
    static List<Book> books = new ArrayList<>();

    static class Book implements Serializable {
        private static final long serialVersionUID = 1L;
        int id;
        String title;
        String author;
        String status; // AVAILABLE / ISSUED
        String issuedTo;
        String issueDate;

        Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.status = "AVAILABLE";
            this.issuedTo = "-";
            this.issueDate = "-";
        }

        @Override
        public String toString() {
            return String.format("ID:%-4d | %-30s | %-20s | %-9s | Issued To: %-15s",
                    id, title, author, status, issuedTo);
        }
    }

    public static void main(String[] args) {
        loadFromFile();
        Scanner sc = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   📚  LIBRARY MANAGEMENT SYSTEM  📚  ║");
        System.out.println("╚══════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            System.out.println("\n┌──────────────────────────────┐");
            System.out.println("│          MAIN MENU           │");
            System.out.println("├──────────────────────────────┤");
            System.out.println("│  1. Add Book                 │");
            System.out.println("│  2. Issue Book               │");
            System.out.println("│  3. Return Book              │");
            System.out.println("│  4. Update Book              │");
            System.out.println("│  5. View All Books           │");
            System.out.println("│  6. Exit                     │");
            System.out.println("└──────────────────────────────┘");
            System.out.print("  Enter your choice: ");

            int choice = -1;
            try { choice = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { System.out.println("  ❌ Invalid input!"); continue; }

            switch (choice) {
                case 1 -> addBook(sc);
                case 2 -> issueBook(sc);
                case 3 -> returnBook(sc);
                case 4 -> updateBook(sc);
                case 5 -> viewAllBooks();
                case 6 -> { saveToFile(); System.out.println("\n  👋 Goodbye! Data saved."); running = false; }
                default -> System.out.println("  ❌ Invalid choice!");
            }
        }
        sc.close();
    }

    static void addBook(Scanner sc) {
        System.out.println("\n━━━━━━━━━━  ADD BOOK  ━━━━━━━━━━");
        int newId = books.isEmpty() ? 1 : books.stream().mapToInt(b -> b.id).max().getAsInt() + 1;

        System.out.print("  Enter Book Title  : ");
        String title = sc.nextLine().trim();
        System.out.print("  Enter Author Name : ");
        String author = sc.nextLine().trim();

        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("  ❌ Title and Author cannot be empty!");
            return;
        }

        Book book = new Book(newId, title, author);
        books.add(book);
        saveToFile();

        System.out.println("\n  ✅ Book Added Successfully!");
        System.out.printf("  Book ID: %d | Title: %s | Author: %s%n", newId, title, author);
    }

    static void issueBook(Scanner sc) {
        System.out.println("\n━━━━━━━━━━  ISSUE BOOK  ━━━━━━━━━━");
        System.out.print("  Enter Book ID to issue: ");
        int id;
        try { id = Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { System.out.println("  ❌ Invalid ID!"); return; }

        Book book = findBook(id);
        if (book == null) { System.out.println("  ❌ Book not found!"); return; }
        if (book.status.equals("ISSUED")) {
            System.out.println("  ❌ Book already issued to: " + book.issuedTo);
            return;
        }

        System.out.print("  Enter Student Name : ");
        String student = sc.nextLine().trim();

        book.status = "ISSUED";
        book.issuedTo = student;
        book.issueDate = new java.text.SimpleDateFormat("dd-MM-yyyy").format(new Date());
        saveToFile();

        System.out.println("\n  ✅ Book Issued Successfully!");
        System.out.println("  ┌────────────────────────────────┐");
        System.out.printf("  │  Book    : %-20s│%n", book.title);
        System.out.printf("  │  Issued To: %-19s│%n", student);
        System.out.printf("  │  Date    : %-20s│%n", book.issueDate);
        System.out.println("  └────────────────────────────────┘");
    }

    static void returnBook(Scanner sc) {
        System.out.println("\n━━━━━━━━━━  RETURN BOOK  ━━━━━━━━━━");
        System.out.print("  Enter Book ID to return: ");
        int id;
        try { id = Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { System.out.println("  ❌ Invalid ID!"); return; }

        Book book = findBook(id);
        if (book == null) { System.out.println("  ❌ Book not found!"); return; }
        if (book.status.equals("AVAILABLE")) {
            System.out.println("  ❌ Book is already available in library!");
            return;
        }

        System.out.println("  Returning book from: " + book.issuedTo);
        book.status = "AVAILABLE";
        book.issuedTo = "-";
        book.issueDate = "-";
        saveToFile();

        System.out.println("  ✅ Book returned successfully!");
    }

    static void updateBook(Scanner sc) {
        System.out.println("\n━━━━━━━━━━  UPDATE BOOK  ━━━━━━━━━━");
        System.out.print("  Enter Book ID to update: ");
        int id;
        try { id = Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { System.out.println("  ❌ Invalid ID!"); return; }

        Book book = findBook(id);
        if (book == null) { System.out.println("  ❌ Book not found!"); return; }

        System.out.println("  Current Title : " + book.title);
        System.out.print("  New Title (Enter to keep): ");
        String newTitle = sc.nextLine().trim();

        System.out.println("  Current Author: " + book.author);
        System.out.print("  New Author (Enter to keep): ");
        String newAuthor = sc.nextLine().trim();

        if (!newTitle.isEmpty()) book.title = newTitle;
        if (!newAuthor.isEmpty()) book.author = newAuthor;
        saveToFile();

        System.out.println("  ✅ Book updated successfully!");
    }

    static void viewAllBooks() {
        System.out.println("\n━━━━━━━━━━  ALL BOOKS  ━━━━━━━━━━");
        if (books.isEmpty()) {
            System.out.println("  📭 No books in library yet.");
            return;
        }
        System.out.println();
        long available = books.stream().filter(b -> b.status.equals("AVAILABLE")).count();
        long issued = books.size() - available;
        for (Book b : books) {
            System.out.println("  " + b);
        }
        System.out.println("\n  Total: " + books.size() + " | Available: " + available + " | Issued: " + issued);
    }

    static Book findBook(int id) {
        return books.stream().filter(b -> b.id == id).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            books = (List<Book>) ois.readObject();
            System.out.println("  📂 " + books.size() + " book(s) loaded from file.");
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not load data: " + e.getMessage());
        }
    }

    static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not save data: " + e.getMessage());
        }
    }
}
