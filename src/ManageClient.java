import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class ManageClient {
    private ConnectionDB connectionDB = new ConnectionDB();
    private CheckInput checkInput = new CheckInput();
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private ShowCars showCars = new ShowCars();


    public int haveAccount() throws IOException, SQLException {
        int idClient;
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



    public int createNewClient() throws IOException, SQLException {
        String name = returnAnswer("name");
        String surname = returnAnswer("surname");
        String login = returnAnswer("login");
        String password = returnAnswer("password");
        if (!checkIfClientAccountExist(login, password)) {
            String sql = "INSERT INTO client (name, surname, login, password) SELECT '"+name+"', '"+surname+"', " +
                    "'"+login+"', '"+password+"' FROM dual WHERE NOT EXISTS (SELECT * FROM client WHERE login='"+login+"' " +
                    "AND password = '"+password+"')";
            connectionDB.createPreparedStatement(sql);
            String sqlId = "SELECT id FROM client WHERE `login` ='"+login+"' AND password='"+password+"' LIMIT 1";
            int idClient = connectionDB.createStatementWithResult(sqlId).getInt(1);
            showCars.showCarsRentedByClient(idClient);
            return idClient;
        } else {
            System.out.println("Such an account already exists!");
            return 0;
        }
    }

    public int loginClient() throws IOException, SQLException {
        String login = returnAnswer("login");
        String password = returnAnswer("password");
        if (checkIfClientAccountExist(login, password)) {
            String sql = "SELECT * FROM client WHERE login ='"+login+"' AND password='"+password+"'";
            connectionDB.createPreparedStatement(sql);
            String sqlId = "SELECT id FROM client WHERE login ='"+login+"' AND password='"+password+"' LIMIT 1";
            int idClient = connectionDB.createStatementWithResult(sqlId).getInt(1);
            return idClient;
        } else {
            System.out.println("Such an account doesn' t exist!");
            return 0;
        }
    }

    private boolean checkIfClientAccountExist(String login, String password) throws SQLException {
        String sql = "SELECT COUNT(*)FROM client WHERE login = '"+login+"' AND password = '"+password+"'";
        int result = connectionDB.createStatementWithResult(sql).getInt(1);
        boolean ifAccountExist = false;
        if (result == 1) {
            ifAccountExist = true;
        } else if (result == 0){
            ifAccountExist = false;
        }
        return ifAccountExist;
    }

    public String returnAnswer(String stringToInput) throws IOException {
        System.out.print("Enter your " + stringToInput + ": ");
        String string = checkInput.notTakeEmptyString();
        return string;
    }
}
