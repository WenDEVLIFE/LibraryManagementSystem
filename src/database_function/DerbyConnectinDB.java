package database_function;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyConnectinDB   {

    private static DerbyConnectinDB instance;

    static String dbName = "jdbc:derby://localhost:1527/LibrarySystem";
    static String user ="root";
    static String password = "root";

    public static DerbyConnectinDB getInstance() {
        if (instance == null) {
            synchronized (DerbyConnectinDB.class) {
                if (instance == null) {
                    instance = new DerbyConnectinDB();
                }
            }
        }
        return instance;
    }

    Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection(dbName, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        try {
            DerbyConnectinDB db = new DerbyConnectinDB();
            Connection connection = db.getConnection();
            if (connection != null) {
                System.out.println("Connection established successfully.");
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
