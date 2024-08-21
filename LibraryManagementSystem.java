import java.sql.*;
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
        private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/LibraryDB";
        String user = "root";
        String password = "pulkit03";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO books(id, title, author, isIssued) VALUES(?, ?, ?, ?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, book.getId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setBoolean(4, book.isIssued());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void issueBook(int bookId) {
        String selectSql = "SELECT * FROM books WHERE id = ?";
        String updateSql = "UPDATE books SET isIssued = true WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            selectStmt.setInt(1, bookId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next() && !rs.getBoolean("isIssued")) {
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
                System.out.println("Book issued: " + rs.getString("title"));
            } else {
                System.out.println("Book not available or already issued.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void returnBook(int bookId) {
        String selectSql = "SELECT * FROM books WHERE id = ?";
        String updateSql = "UPDATE books SET isIssued = false WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            selectStmt.setInt(1, bookId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next() && rs.getBoolean("isIssued")) {
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
                System.out.println("Book returned: " + rs.getString("title"));
            } else {
                System.out.println("Book not found in issued books.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayBooks() {
        String sql = "SELECT * FROM books";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Books in library:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") + ", Author: " + rs.getString("author") + ", Issued: " + rs.getBoolean("isIssued"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
        sc.nextLine(); 
        while (x!=5) {
            switch (x) {
                case 1:
                    library.displayBooks();
                    break;
                case 2:
                    System.out.println("Enter the ID of the book: ");
                    int id = sc.nextInt();
		    sc.nextLine();
                    System.out.println("Enter Name of the book: ");
                    String name = sc.nextLine();
		
                    System.out.println("Enter Name of Author: ");
                    String aname = sc.nextLine();

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
			break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println("\nEnter 1 to Display all books, 2 to Add a book, 3 to Issue a book, 4 to Return a book, 5 to Exit");
            x = sc.nextInt();
        }
    }
}
