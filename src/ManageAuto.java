import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManageAuto extends CheckInput{
    static ArrayList<Auto> allAuto = new ArrayList<>();
    static ArrayList<Auto> rentedAuto = new ArrayList<>();
    private ArrayList<Auto> availableCars = allAuto;
    private ManageAccounts manageAccounts = new ManageAccounts();

    public void rentACar() throws IOException {
        if (manageAccounts.loginOrRegister()){
            boolean flag = true;
            while(flag) {
                boolean flagCorrect = true;
                while (flagCorrect) {
                    System.out.println("\nWhich car you want to rent? Enter the car ID number from below list: ");
                    showAllCars();
                    int number = stringToInt();
                    if (number <= allAuto.size() && number > 0) {
                        Auto autoToRent = allAuto.get(number-1);
                        System.out.println(autoToRent);
                        rentedAuto.add(autoToRent);
                        availableCars.remove(autoToRent);
                        Client.client.addCarToRentedCarsByClient(autoToRent);
                        System.out.println("You rented this car. ");
                        flagCorrect = false;
                    } else {
                        System.out.println("Enter correct value! ");
                        flagCorrect = true;
                    }
                }
                flag = false;
            }
        }
    }

    public void returnCar(BufferedReader bufferedReader) throws IOException {
        boolean flag = true;
        while(flag) {
            boolean flagCorrect = true;
            while (flagCorrect) {
                System.out.println("\nWhich car you want to return? Enter the car ID number from below list: ");
                showAllRentedCars();
                int number = stringToInt();
                if (number <= rentedAuto.size() && number > 0) {
                    Auto autoToRemove = allAuto.get(number-1);
                    rentedAuto.remove(autoToRemove);
                    availableCars.add(autoToRemove);
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

    public void showAvailableCars() {
        System.out.println("-----------------------------------------------------------LIST OF AVAILABLE CARS-----------------------------------------------------------");
        showAllItem(availableCars);
        System.out.println();
    }

    public void showAllCars() {
        System.out.println("-----------------------------------------------------------LIST OF ALL CARS-----------------------------------------------------------");
        showAllItem(allAuto);
        System.out.println();
    }


    private void showAllRentedCars() {
        System.out.println("-----------------------------------------------------------LIST OF RENTED CARS-----------------------------------------------------------");
        showAllItem(rentedAuto);
        System.out.println();
    }

    private void showAllItem(ArrayList listOfItem) {
        int counter = 1;
        if (listOfItem.size() != 0) {
            for (Object object : listOfItem) {
                System.out.println(counter + ". " + object);
                counter++;
            }
        } else {
            System.out.println("Sorry, currently, the list is empty.");
        }
    }
}
