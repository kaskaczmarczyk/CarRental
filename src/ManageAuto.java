import java.io.IOException;
import java.sql.*;

public class ManageAuto extends CheckInput{
    private Connection connection = null;
    private CheckInput checkInput = new CheckInput();
    java.util.Date date = new java.util.Date();
    private Date startTime = new Date(date.getTime());
    private Date endTime = new Date(date.getTime());

    public void rentACar(int id) throws IOException {
        ShowCars showCars = new ShowCars();
        int clientId = id;
        boolean flag = true;
        while (flag) {
            boolean flagCorrect = true;
            while (flagCorrect) {
                showCars.showAvailableCarsInRental();
                System.out.println("\nWhich car you want to rent? Enter the car ID number from above list: ");
                int autoId = stringToInt();
                if (autoId <= countAvailableCars() && autoId > 0) {
                    String qwery = "SELECT id FROM (SELECT id FROM allauto WHERE isAvailable = 1 LIMIT "+autoId+") " +
                            "AS num ORDER BY ID DESC LIMIT 1";
                    try {
                        Statement stmId = connection.createStatement();
                        ResultSet resultSet = stmId.executeQuery(qwery);
                        while (resultSet.next()) {
                            int num = resultSet.getInt(1);
                            autoId = num;
                        }
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                    }
                    setCarAsRented(autoId);
                    createRentail(clientId, autoId);
                    System.out.println("You rented this car. ");
                    System.out.print("Do you want to rent successive car? Y/N ");
                    String answer = checkInput.notTakeEmptyString();
                    if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                        continue;
                    } else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                        break;
                    } else {
                        System.out.println("Enter the correct character! ");
                    }
                    flagCorrect = false;
                } else {
                    System.out.println("Enter correct value! ");
                    flagCorrect = true;
                }
            }
            flag = false;
        }
    }

    private int countAvailableCars() {
        int count = 0;
        String sgl = "SELECT COUNT(*) FROM allauto WHERE isAvailable = 1";
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sgl);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException exc) {
            System.err.println("Error when reading the auto data.");
            exc.printStackTrace();
        }
        return count;
    }

    private int countRentedCarsByClient(int idClient) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM rentaldetails INNER JOIN client ON client.id = rentaldetails.idClient INNER " +
                "JOIN allauto ON allauto.id = rentaldetails.idAuto WHERE client.id = "+idClient+"";
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException exc) {
            System.err.println("Error when reading the auto data.");
            exc.printStackTrace();
        }
        return count;
    }

    private void setCarAsRented(int id) {
        String sgl = "UPDATE allauto SET isAvailable = 0 WHERE id = '"+id+"'";
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sgl);
            prepstm.execute();
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when reading the auto data.");
            exc.printStackTrace();
        }
    }

    private void setCarAsAvailable(int id) {
        String sgl = "UPDATE allauto SET isAvailable= 1 WHERE id = '"+id+"'";
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sgl);
            prepstm.execute();
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when reading the auto data.");
            exc.printStackTrace();
        }
    }

    private void createRentail(int clientID, int autoID) {
        String sql = "INSERT INTO rentalDetails (idClient, idAuto, startTime) VALUES ('"+clientID+"', '"+autoID+"', '"+startTime+"')";
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);

        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstmt = connection.prepareStatement(sql);
            prepstmt.executeUpdate();
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when creating new rentals.");
            exc.printStackTrace();
        }
    }

    private void closeRentail(int clientID, int autoID) {
        String sql = "UPDATE rentaldetails SET endTime = '"+endTime+"' WHERE idAuto = '"+autoID+"' AND " +
                "idClient= '"+clientID+"'";
        System.out.println("You rented this car for " + countDaysOfRental() + " days.");
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);

        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql);
            prepstm.executeUpdate();
        } catch (SQLException exc) {
            System.err.println("Error when closing rentals.");
            exc.printStackTrace();
        }
    }

    public void returnCar(int id) throws IOException {
        ShowCars showCars = new ShowCars();
        int clientId = id;
        boolean flag = true;
        while (flag) {
            boolean flagCorrect = true;
            while (flagCorrect) {
                showCars.showCarsRentedByClient(clientId);
                System.out.println("\nWhich car you want to return? Enter the car ID number from above list: ");
                int autoId = stringToInt();
                if (autoId <= countRentedCarsByClient(clientId) && autoId > 0) {
                    String qwery = "SELECT * FROM (SELECT allauto.id FROM ((rentaldetails INNER JOIN allauto " +
                            "ON allauto.id = rentaldetails.idAuto) INNER JOIN client " +
                            "ON rentaldetails.idClient = client.id) WHERE client.id = '"+clientId+"' " +
                            "AND rentaldetails.endTime = 0000-00-00) AS num";
                    try {
                        Statement stmId = connection.createStatement();
                        ResultSet resultSet = stmId.executeQuery(qwery);
                        while (resultSet.next()) {
                            int num = resultSet.getInt(1);
                            autoId = num;
                        }
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                    }
                    setCarAsAvailable(autoId);
                    closeRentail(clientId, autoId);
                    System.out.println("You returned this car. ");
                    flagCorrect = false;
                } else {
                    System.out.println("Enter correct value! ");
                    flagCorrect = true;
                }
            }
            flag = false;
        }
    }

    public int countDaysOfRental() {
        String sql = "SELECT DATEDIFF("+endTime+", "+startTime+")";
        int days = 0;
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                days = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when reading the auto data.");
            exc.printStackTrace();
        }
        return days;
    }
}
