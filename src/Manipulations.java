import java.sql.*;
import java.util.Scanner;

public class Manipulations {
    static Scanner scanner = new Scanner(System.in);
    public static void viewAllBooks() throws SQLException {
        Connection conn = DatabaseUtil.getConnection();
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
            System.out.println("_______________________________________________________________________________________________________________________________________________________________________________");
            System.out.println("Title: " + title + ", Authors: " + authors + ", Category: " + category + ", Publisher: " + publisher + ", Price: $" + price + ", Publication Date: " + publicationDate);        }
        System.out.println("_______________________________________________________________________________________________________________________________________________________________________________");
        rs.close();
        stmt.close();
    }
    public   void addNewBook() throws SQLException {
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
        Connection connection = DatabaseUtil.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
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
    public void searchBookByTitle() throws SQLException {
        Connection connection = DatabaseUtil.getConnection();
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
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
    }
}
