import java.util.ArrayList;

public class Client extends Person{

    private static ArrayList<Auto> rentedCarsByClient;

    Client(String name, String surname) {
        super(name, surname);
        rentedCarsByClient = new ArrayList<>();
    }

    public static void showAllRentedCarsByClient() {
        System.out.println("-----------------------------------------------------------LIST OF RENTED CARS BY CLIENT-----------------------------------------------------------");
        for (Auto auto: rentedCarsByClient) {
            System.out.println(auto);
        }
    }
}
