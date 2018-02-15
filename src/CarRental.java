import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CarRental {

    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        CarRental carRental = new CarRental();
        BufferedReader bufferedReader = carRental.bufferedReader;
        SportAuto auto1 = new SportAuto("Peugeot", 1.4, true, true);
        Auto auto2 = new Auto("Audi", 1.6);
        Auto auto3 = new Auto("Honda", 2.0);
        Auto auto4 = new Auto("Opel", 2.5);
        Auto auto5 = new Auto("Subaru", 3.0);
        ManageAuto.allAuto.add(auto1);
        ManageAuto.allAuto.add(auto2);
        ManageAuto.allAuto.add(auto3);
        ManageAuto.allAuto.add(auto4);
        ManageAuto.allAuto.add(auto5);


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
                    ManageAuto rentACar = new ManageAuto();
                    rentACar.rentACar();
                    break;
                case "2":
                    ManageAuto returnCar = new ManageAuto();
                    returnCar.returnCar(bufferedReader);
                    break;
                case "3":
                    ManageAuto showAvailableCars = new ManageAuto();
                    showAvailableCars.showAvailableCars();
                    break;
                case "4":
                    ManageAuto showAllCars = new ManageAuto();
                    showAllCars.showAllCars();
                    break;
                case "5":
                    ManageAuto loginToAccount = new ManageAuto();
                    loginToAccount.login();
                    break;
                case "6":
                    ManageAuto registerNewAccount = new ManageAuto();
                    registerNewAccount.register();
                    break;
                default:
                    System.out.println("...");
            }
        }
    }
}

