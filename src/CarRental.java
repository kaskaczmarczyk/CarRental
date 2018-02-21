import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CarRental {

    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        CarRental carRental = new CarRental();
        BufferedReader bufferedReader = carRental.bufferedReader;

        while (true) {
            System.out.println("\nWelcome in our car rental. Choose one of the following options: ");
            System.out.println("1. Rent a car");
            System.out.println("2. Return the car");
            System.out.println("3. Show available cars");
            System.out.println("4. Show all cars");
            System.out.println("5. Login");
            System.out.println("6. Register");

            String chosenModule = bufferedReader.readLine();

            switch (chosenModule) {
                case "1" :
                    ManageClient manageClientRent = new ManageClient();
                    ManageAuto rentCar = new ManageAuto();
                    rentCar.rentACar(manageClientRent.haveAccount());
                    break;
                case "2":
                    ManageClient manageClientReturn = new ManageClient();
                    ManageAuto returnCar = new ManageAuto();
                    returnCar.returnCar(manageClientReturn.loginClient());
                    break;
                case "3":
                    ShowCars showAvailableCars = new ShowCars();
                    showAvailableCars.showAvailableCarsInRental();
                    break;
                case "4":
                    ShowCars showAllCars = new ShowCars();
                    showAllCars.showAllCarsInRental();
                    break;
                case "5":
                    ManageClient loginClient = new ManageClient();
                    loginClient.loginClient();
                    break;
                case "6":
                    ManageClient registerNewClient = new ManageClient();
                    registerNewClient.createNewClient();
                    break;
                default:
                    System.out.println("...");
            }
        }
    }
}

