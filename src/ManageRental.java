import java.io.IOException;
import java.sql.*;

public class ManageRental extends CheckInput{
    private ConnectionDB connectionDB = new ConnectionDB();
    private CheckInput checkInput = new CheckInput();
    private java.util.Date date = new java.util.Date();
    private Date startTime = new Date(date.getTime());
    private Date endTime = new Date(date.getTime());

    public void rentACar(int clientId) throws IOException, SQLException {
        ShowCars showCars = new ShowCars();
        boolean flag = true;
        while (flag) {
            boolean flagCorrect = true;
            while (flagCorrect) {
                if (countAvailableCars() > 0) {
                    showCars.showAvailableCarsInRental();
                    System.out.println("\nWhich car you want to rent? Enter the car ID number from above list: ");
                    int autoId = stringToInt();
                    if (autoId <= countAvailableCars() && autoId > 0) {
                        String qwery = "SELECT id FROM (SELECT id FROM allauto WHERE isAvailable = 1 LIMIT "+autoId+") " +
                                "AS num ORDER BY ID DESC LIMIT 1";
                        autoId = connectionDB.createStatementWithResult(qwery).getInt(1);
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
                } else {
                    System.out.println("Sorry, there aren't available cars to rent. ");
                    break;
                }
            }
            flag = false;
        }
    }

    private int countAvailableCars() throws SQLException {
        String sql = "SELECT COUNT(*) FROM allauto WHERE isAvailable = 1";
        return connectionDB.createStatementWithResult(sql).getInt(1);
    }

    private int countRentedCarsByClient(int idClient) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rentaldetails INNER JOIN client ON client.id = rentaldetails.idClient INNER " +
                "JOIN allauto ON allauto.id = rentaldetails.idAuto WHERE client.id = "+idClient+"";
        return connectionDB.createStatementWithResult(sql).getInt(1);
    }

    private int countRentedCarsByClientNow(int idClient) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rentaldetails INNER JOIN client ON client.id = rentaldetails.idClient INNER " +
                "JOIN allauto ON allauto.id = rentaldetails.idAuto WHERE client.id = "+idClient+" AND rentaldetails.endTime = 0000-00-00";
        return connectionDB.createStatementWithResult(sql).getInt(1);
    }

    private void setCarAsRented(int id) {
        String sql = "UPDATE allauto SET isAvailable = 0 WHERE id = '"+id+"'";
        connectionDB.createPreparedStatement(sql);
    }

    private void setCarAsAvailable(int id) {
        String sql = "UPDATE allauto SET isAvailable= 1 WHERE id = '"+id+"'";
        connectionDB.createPreparedStatement(sql);
    }

    private void createRentail(int clientID, int autoID) {
        String sql = "INSERT INTO rentalDetails (idClient, idAuto, startTime) VALUES ('"+clientID+"', '"+autoID+"', '"+startTime+"')";
        connectionDB.createPreparedStatement(sql);
    }

    private void closeRentail(int clientID, int autoID) throws SQLException {
        String sql = "UPDATE rentaldetails SET endTime = '"+endTime+"' WHERE idAuto = '"+autoID+"' AND " +
                "idClient= '"+clientID+"'";
        int daysOfRental = countDaysOfRental(clientID, autoID);
        System.out.println("You rented this car for " + daysOfRental + " days.");
        connectionDB.createPreparedStatement(sql);
        System.out.println("You have to pay " + countHowMuchPay(daysOfRental, getPricePerDay(autoID)) + " PLN.");
    }

    private double getPricePerDay(int autoId) throws SQLException {
        String sql = "SELECT pricePerDay FROM allAuto WHERE id = '"+autoId+"'";
        return connectionDB.createStatementWithResult(sql).getDouble(1);
    }

    public void returnCar(int clientId) throws IOException, SQLException {
        ShowCars showCars = new ShowCars();
        boolean flag = true;
        while (flag) {
            boolean flagCorrect = true;
            while (flagCorrect) {
                if (countRentedCarsByClientNow(clientId) == 0) {
                    System.out.println("You don't have any rentals.");
                    break;
                } else {
                    showCars.showCarsRentedByClient(clientId);
                    System.out.println("\nWhich car you want to return? Enter the car ID number from above list: ");
                    int autoId = stringToInt();
                    if (autoId <= countRentedCarsByClient(clientId) && autoId > 0) {
                        String qwery = "SELECT * FROM (SELECT allauto.id FROM ((rentaldetails INNER JOIN allauto " +
                                "ON allauto.id = rentaldetails.idAuto) INNER JOIN client " +
                                "ON rentaldetails.idClient = client.id) WHERE client.id = '"+clientId+"' " +
                                "AND rentaldetails.endTime = 0000-00-00 LIMIT "+autoId+") AS num ORDER BY ID DESC LIMIT 1";
                        autoId = connectionDB.createStatementWithResult(qwery).getInt(1);
                        setCarAsAvailable(autoId);
                        closeRentail(clientId, autoId);
                        System.out.println("You returned this car. ");
                        flagCorrect = false;
                    } else {
                        System.out.println("Enter correct value! ");
                        flagCorrect = true;
                    }
                }
            }
            flag = false;
        }
    }

    private int countDaysOfRental(int idClient, int idAuto) throws SQLException {
        Date startTime = getTimeStart(getIdRentails(idClient, idAuto));
        String sql = "SELECT DATEDIFF('"+endTime+"', '"+startTime+"')";
        return connectionDB.createStatementWithResult(sql).getInt(1);
    }

    private double countHowMuchPay(int days, double pricePerDay) {
        return  pricePerDay * days;
    }

    private int getIdRentails(int idClient, int idAuto) throws SQLException {
        String sql = "SELECT id FROM rentaldetails WHERE idClient = '"+idClient+"' AND idAuto = '"+idAuto+"' AND endTime = 0000-00-00";
        return connectionDB.createStatementWithResult(sql).getInt(1);
    }

    private Date getTimeStart(int idRentails) throws SQLException {
        String sql = "SELECT startTime FROM rentaldetails WHERE id = '"+idRentails+"'";
        return connectionDB.createStatementWithResult(sql).getDate(1);
    }
}
