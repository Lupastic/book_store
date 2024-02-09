import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "baktybek";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Scanner scanner = new Scanner(System.in)) {

            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            System.out.println("Connected to database successfully.");

            UserRegistrationService userService = new UserRegistrationService();

            boolean userLoggedIn = false;

            while (!userLoggedIn) {
                System.out.println("\n1. Register");
                System.out.println("2. Login");
                System.out.print("Enter your choice (1 or 2): ");
                int authChoice = scanner.nextInt();
                scanner.nextLine();

                if (authChoice == 1) {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (userService.registerUser(username, password, conn)) {
                        System.out.println("Registration successful.");
                    } else {
                        System.out.println("Registration failed.");
                    }
                } else if (authChoice == 2) {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (userService.loginUser(username, password, conn)) {
                        System.out.println("Login successful.");
                        userLoggedIn = true;
                    } else {
                        System.out.println("Login failed. Please check your username and password.");
                    }
                }
            }

            if (userLoggedIn) {
                while (true) {
                    System.out.println("\n3. View all books");
                    System.out.println("4. Add a new book");
                    System.out.println("5. Search for a book by title");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 3:
                            viewAllBooks(conn);
                            break;
                        case 4:
                            addNewBook(conn, scanner);
                            break;
                        case 5:
                            searchBookByTitle(conn, scanner);
                            break;
                        case 6:
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid choice, please try again.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void viewAllBooks(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT title, authors, category, publisher, publish_date, price FROM books";
        ResultSet rs;
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String title = rs.getString("title");
            String authors = rs.getString("authors");
            String category = rs.getString("category");
            String publisher = rs.getString("publisher");
            double price = rs.getDouble("price");
            Date publicationDate = rs.getDate("publish_date");
            System.out.println("Title: " + title + ", Authors: " + authors + ", Category: " + category + ", Publisher: " + publisher + ", Price: $" + price + ", Publication Date: " + publicationDate);        }
        rs.close();
        stmt.close();
    }
    private static void addNewBook(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter authors: ");
        String authors = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter publication date (YYYY-MM-DD): ");
        String publicationDate = scanner.next();
        String sql = "INSERT INTO books (title, authors, category, publisher, price, publish_date) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, title);
        pstmt.setString(2, authors);
        pstmt.setString(3, category);
        pstmt.setString(4, publisher);
        pstmt.setDouble(5, price);
        pstmt.setDate(6, Date.valueOf(publicationDate));
        int rowsInserted = pstmt.executeUpdate();
        System.out.println(rowsInserted + " book(s) inserted.");
        pstmt.close();
    }
    private static void searchBookByTitle(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + keyword + "%");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String title = rs.getString("title");
            String authors = rs.getString("authors");
            String category = rs.getString("category");
            String publisher = rs.getString("publisher");
            double price = rs.getDouble("price");
            Date publicationDate = rs.getDate("publish_date");
            System.out.println("Title: " + title + ", Authors: " + authors + ", Category: " + category + ", Publisher: " + publisher + ", Price: $" + price + ", Publication Date: " + publicationDate);        }
        rs.close();
        pstmt.close();
}}
