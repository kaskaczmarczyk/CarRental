import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {

    public static final String driverName = "com.mysql.jdbc.Driver";
    public static final String database = "carrental";
    public static final String url = "jdbc:mysql://localhost/carrental";
    public static String userID = "root";
    public static String password = "";

    public Connection connection;
    private Statement stmt;

    ConnectionDB(){
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException exc) {
            System.err.println("No driver JDBC");
            exc.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, userID, password);
            stmt = connection.createStatement();
        } catch (SQLException exc) {
            System.err.println("Problem with opening the connection");
            exc.printStackTrace();
        }

        connect(database);
    }

    public static Connection connect(String database) {
        Connection connection = null;
        try {
            Class.forName(ConnectionDB.driverName);
            connection = DriverManager.getConnection(url, userID, password);
            System.out.println("Connection with database: " + database);
        }
        catch (Exception exc) {
            System.out.println("Error in connection with the database: \n" + exc.getMessage());
            return null;
        }
        return connection;
    }
}
