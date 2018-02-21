import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class ManageClient {
    private Connection connection = null;
    private CheckInput checkInput = new CheckInput();
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public int haveAccount() throws IOException {
        int idClient = 0;
        while (true) {
            System.out.print("Do you have a rental account? Y/N ");
            String answer = bufferedReader.readLine();
            if (answer.equalsIgnoreCase("Y")) {
                idClient = loginClient();
                break;
            } else if (answer.equalsIgnoreCase("N")) {
                idClient = createNewClient();
                break;
            } else {
                System.out.println("Enter the correct character! ");
            }
        }
        return idClient;
    }

    public int createNewClient() throws IOException {
        System.out.print("Enter your name: ");
        String name = checkInput.notTakeEmptyString();
        System.out.print("Enter your surname: ");
        String surname = checkInput.notTakeEmptyString();
        System.out.print("Enter your login: ");
        String login = checkInput.notTakeEmptyString();
        System.out.print("Enter your password: ");
        String password = checkInput.notTakeEmptyString();
        String sgl = "INSERT INTO client (name, surname, login, password) SELECT '"+name+"', '"+surname+"', " +
                "'"+login+"', '"+password+"' FROM dual WHERE NOT EXISTS (SELECT * FROM client WHERE login='"+login+"' " +
                "AND password = '"+password+"')";
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sgl);
            prepstm.execute();
        } catch (SQLException exc) {
            System.err.println("Error when reading the client data.");
            exc.printStackTrace();
        }
        String sqlId = "SELECT id FROM client WHERE `login` ='"+login+"' AND password='"+password+"' LIMIT 1";
        int id = 0;
        try {
            Statement stmId = connection.createStatement();
            ResultSet resultSet = stmId.executeQuery(sqlId);
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when reading the client ID number.");
            exc.printStackTrace();
        }
        ShowCars showCars = new ShowCars();
        showCars.showCarsRentedByClient(id);
        return id;
    }

    public int loginClient() throws IOException {
        System.out.print("Enter your login: ");
        String login = checkInput.notTakeEmptyString();
        System.out.print("Enter your password: ");
        String password = checkInput.notTakeEmptyString();
        String sql = "SELECT * FROM client WHERE login ='"+login+"' AND password='"+password+"'";
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql);
            prepstm.execute();
            System.out.println("You login to your account.");
        } catch (SQLException exc) {
            System.err.println("Error when reading the client data.");
            exc.printStackTrace();
        }
        String sqlId = "SELECT id FROM client WHERE login ='"+login+"' AND password='"+password+"' LIMIT 1";
        int id = 0;
        try {
            Statement stmId = connection.createStatement();
            ResultSet resultSet = stmId.executeQuery(sqlId);
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when reading the client ID number.");
            exc.printStackTrace();
        }
        return id;
    }
}
