import java.sql.*;

public class ConnectionDB {

    public static final String database = "carrental";
    public static final String url = "jdbc:mysql://localhost/carrental";
    private static String userID = "root";
    public static String password = "";
    public Connection connection;

    public Connection connect() {
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
            return connection;
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
            return null;
        }
    }

    public ResultSet createStatementWithResult(String sql) {
        try {
            Statement statement = connect().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.err.println("Error when reading the data.");
            exc.printStackTrace();
            return null;
        }
    }

    public void createPreparedStatement(String sql) {
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException exc) {
            System.err.println("Error when creating prepared statement.");
            exc.printStackTrace();
        }
    }
}
