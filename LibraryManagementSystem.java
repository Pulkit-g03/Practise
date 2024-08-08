import java.util.*;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean isIssued) {
        this.isIssued = isIssued;
    }

 public String displayBookInfo() {
        return "Title: " + title + ", Author: " + author;
    }

}

class Library {
    private List<Book> books;
    private Map<Integer, Book> issuedBooks;

    public Library() {
        books = new ArrayList<>();
        issuedBooks = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void issueBook(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId && !book.isIssued()) {
                book.setIssued(true);
                issuedBooks.put(bookId, book);
                System.out.println("Book issued: " + book);
                return;
            }
        }
        System.out.println("Book not available or already issued.");
    }

    public void returnBook(int bookId) {
        if (issuedBooks.containsKey(bookId)) {
            Book book = issuedBooks.get(bookId);
            book.setIssued(false);
            issuedBooks.remove(bookId);
            System.out.println("Book returned: " + book);
        } else {
            System.out.println("Book not found in issued books.");
        }
    }

    public void displayBooks() {
        System.out.println("Books in library:");
    for (Book book : books) {
        System.out.println(book.displayBookInfo());
    }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        int x=0;
        System.out.println("\nEnter 1 to Display all books, 2 to Add a book, 3 to Issue a book, 4 to Return a book, 5 to Exit");
        x = sc.nextInt();
        sc.nextLine(); // Consume the newline character after nextInt()
        while (x!=5) {
            switch (x) {
                case 1:
                    library.displayBooks();
                    break;
                case 2:
                    System.out.println("Enter Name of the book: ");
                    String name = sc.nextLine();
                    System.out.println("Enter Name of Author: ");
                    String aname = sc.nextLine();
                    System.out.println("Enter the ID of the book: ");
                    int id = sc.nextInt();
                    library.addBook(new Book(id, name, aname));
                    break;
                case 3:
                    System.out.println("Enter the ID of the book to issue: ");
                    int issueId = sc.nextInt();
                    library.issueBook(issueId);
                    break;
                case 4:
                    System.out.println("Enter the ID of the book to return: ");
                    int returnId = sc.nextInt();
                    library.returnBook(returnId);
                    break;
                case 5:
                    System.out.println("Exiting the system.");
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println("\nEnter 1 to Display all books, 2 to Add a book, 3 to Issue a book, 4 to Return a book, 5 to Exit");
            x = sc.nextInt();
        }
    }
}
