import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManageAuto extends CheckInput{
    static ArrayList<Auto> allAuto = new ArrayList<>();
    private static ArrayList<Auto> rentedAuto = new ArrayList<>();
    static ArrayList<Auto> availableCars = new ArrayList<>();
    private ManageAccounts manageAccounts = new ManageAccounts();
    private CheckInput checkInput = new CheckInput();

    public void rentACar() throws IOException {
        if (manageAccounts.loginOrRegister()){
            boolean flag = true;
            while(flag) {
                boolean flagCorrect = true;
                while (flagCorrect) {
                    System.out.println("\nWhich car you want to rent? Enter the car ID number from below list: ");
                    showAvailableCars();
                    int number = stringToInt();
                    if (number <= availableCars.size() && number > 0) {
                        Auto autoToRent = availableCars.get(number-1);
                        System.out.println(autoToRent);
                        rentedAuto.add(autoToRent);
                        availableCars.remove(number-1);
                        Client.client.addCarToRentedCarsByClient(autoToRent);
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
    }

    public void returnCar(BufferedReader bufferedReader) throws IOException {
        if(manageAccounts.loginOrRegister()) {
            boolean flag = true;
            while (flag) {
                boolean flagCorrect = true;
                while (flagCorrect) {
                    System.out.println("\nWhich car you want to return? Enter the car ID number from below list: ");
                    showAllRentedCars();
                    int number = stringToInt();
                    if (number <= rentedAuto.size() && number > 0) {
                        Auto autoToRemove = rentedAuto.get(number - 1);
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
