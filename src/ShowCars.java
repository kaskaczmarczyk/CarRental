import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowCars {
    private ConnectionDB connectionDB = new ConnectionDB();

    private void showCarsInRental(String qwery){
        Connection connection = connectionDB.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(qwery);
            int autoCounter = 0;
            while (resultSet.next()) {
                autoCounter++;
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                double engineCapacity = resultSet.getDouble("capacity");
                String fuel = resultSet.getString("fuel");
                int km = resultSet.getInt("km");
                int numberOfSeats = resultSet.getInt("numberOfSeats");
                double pricePerDay = resultSet.getDouble("pricePerDay");
                boolean airConditioning = resultSet.getBoolean("airConditioning");
                System.out.format("%s. %s %s, capacity: %s, fuel: %s, horsepower: %s, number of seats: %s, price per day: %s, " +
                        "air conditioning: %s\n", autoCounter, brand, model, engineCapacity, fuel, km, numberOfSeats, pricePerDay, airConditioning);
            }
        } catch (SQLException exc) {
            System.err.println("Error when reading the client data.");
            exc.printStackTrace();
        }
    }

    public void showAllCarsInRental() {
        System.out.println("-------------------ALL CARS-------------------");
        String qwery = "SELECT * FROM allauto";
        showCarsInRental(qwery);
    }

    public void showAvailableCarsInRental() {
        String qwery = "SELECT * FROM allauto WHERE isAvailable = 1";
        System.out.println("-------------------AVAILABLE CARS-------------------");
        showCarsInRental(qwery);
    }

    public void showCarsRentedByClient(int idClient) {
        String qwery = "SELECT allauto.id, allauto.brand, allauto.model, allauto.capacity, allauto.fuel, allauto.KM, " +
                "allauto.numberOfSeats, allauto.airConditioning, allauto.year, allauto.pricePerDay, allauto.isAvailable " +
                "FROM ((rentaldetails INNER JOIN allauto ON allauto.id = rentaldetails.idAuto) INNER JOIN client ON " +
                "rentaldetails.idClient = client.id) WHERE client.id = "+idClient+" AND rentaldetails.endTime = 0000-00-00";
        System.out.println("-------------------RENTED CARS BY CLIENT-------------------");
        showCarsInRental(qwery);
    }
}
