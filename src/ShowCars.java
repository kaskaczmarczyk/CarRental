import java.io.IOException;
import java.sql.*;

public class ShowCars {
    private Connection connection = null;
    private CheckInput checkInput = new CheckInput();

    public void showCarsInRental(String qwery) throws IOException {
        String sgl = qwery;
        try {
            connection = DriverManager.getConnection(ConnectionDB.url, ConnectionDB.userID, ConnectionDB.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sgl);
            int id = 0;
            while (rs.next()) {
                id++;
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                double engineCapacity = rs.getDouble("capacity");
                String fuel = rs.getString("fuel");
                int km = rs.getInt("km");
                int numberOfSeats = rs.getInt("numberOfSeats");
                double pricePerDay = rs.getDouble("pricePerDay");
                boolean airConditioning = rs.getBoolean("airConditioning");
                System.out.format("%s. %s %s, capacity: %s, fuel: %s, horsepower: %s, number of seats: %s, price per day: %s, " +
                        "air conditioning: %s\n", id, brand, model, engineCapacity, fuel, km, numberOfSeats, pricePerDay, airConditioning);
            }
            st.close();
        } catch (SQLException exc) {
            System.err.println("Error when reading the client data.");
            exc.printStackTrace();
        }
    }

    public void showAllCarsInRental() throws IOException {
        System.out.println("-------------------ALL CARS-------------------");
        String qwery = "SELECT * FROM allauto";
        showCarsInRental(qwery);
    }

    public void showAvailableCarsInRental() throws IOException {
        String qwery = "SELECT * FROM allauto WHERE isAvailable = 1";
        System.out.println("-------------------AVAILABLE CARS-------------------");
        showCarsInRental(qwery);
    }

    public void showRentedCarsInRental() throws IOException {
        String qwery = "SELECT * FROM allauto WHERE isAvailable = 0";
        System.out.println("-------------------RENTED CARS-------------------");
        showCarsInRental(qwery);
    }

    public void showCarsRentedByClient(int idClient) throws IOException {
        String qwery = "SELECT allauto.id, allauto.brand, allauto.model, allauto.capacity, allauto.fuel, allauto.KM, " +
                "allauto.numberOfSeats, allauto.airConditioning, allauto.year, allauto.pricePerDay, allauto.isAvailable " +
                "FROM ((rentaldetails INNER JOIN allauto ON allauto.id = rentaldetails.idAuto) INNER JOIN client ON " +
                "rentaldetails.idClient = client.id) WHERE client.id = "+idClient+" AND rentaldetails.endTime = 0000-00-00";
        System.out.println("-------------------RENTED CARS BY CLIENT-------------------");
        showCarsInRental(qwery);
    }
}
