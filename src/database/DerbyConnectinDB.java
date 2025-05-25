package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyConnectinDB   {

    private static DerbyConnectinDB instance;

    String dbName = "jdbc:derby:LibrarySystem;create=true";
    String user ="root";
    String password = "root";


    private  Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(dbName, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main (String[] args) {
        DerbyConnectinDB db = new DerbyConnectinDB();
        Connection connection = db.getConnection();
        if (connection != null) {
            System.out.println("Connection established successfully.");
        } else {
            System.out.println("Failed to establish connection.");
        }
    }
}
