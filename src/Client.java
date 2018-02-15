import java.util.ArrayList;

public class Client extends Person{

    public static ArrayList<Auto> rentedCarsByClient = new ArrayList<>();
    public static ArrayList<Client> clients = new ArrayList<>();

    Client(String name, String surname) {
        super(name, surname);
    }

    public static Client client;

    public void addClient(Client client) {
        clients.add(client);
    }

    public void addCarToRentedCarsByClient(Auto auto) {
        rentedCarsByClient.add(auto);
    }

    public Client getClient() {
        return this;
    }

    public void showAllRentedCarsByClient() {
        System.out.println("-----------------------------------------------------------LIST OF RENTED CARS BY CLIENT-----------------------------------------------------------");
        for (Auto auto: rentedCarsByClient) {
            System.out.println(auto);
        }
    }
}
