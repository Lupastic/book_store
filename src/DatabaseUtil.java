import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static String URL = "jdbc:postgresql://localhost:5432/postgres";
    private  static String USER = "postgres";
    private static String PASS = "baktybek";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASS);
    }
}
