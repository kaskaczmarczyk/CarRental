import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManageAuto extends CheckInput{
    static ArrayList<Auto> allAuto = new ArrayList<>();
    private static ArrayList<Auto> rentedAuto = new ArrayList<>();
    private static ArrayList<Auto> availableCars = allAuto;

    private boolean loginOrRegister() throws IOException {
        while (true) {
            System.out.print("Do you have an account in our car rental? Y/N ");
            String answer = notTakeEmptyString();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                login();
                break;
            } else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                System.out.println("You must create a new account. ");
                register();
                break;
            } else {
                System.out.println("Enter the correct character! ");
            }
        }
        return true;
    }

    public void login() throws IOException {
        boolean flag = true;
        while (flag) {
            if (!Database.DATABASE.accountsDB.isEmpty()) {
                System.out.print("Enter yor login: ");
                String login = notTakeEmptyString();
                System.out.print("Enter your password: ");
                String password = notTakeEmptyString();
                for (Account account: Database.DATABASE.accountsDB) {
                    if (account.getLogin().equals(login) && account.getPassword().equals(password)) {
                        System.out.println("You login to your account. ");
                        flag = false;
                    } else {
                        System.out.println("Such account doesn't exist! ");
                    }
                }
            } else {
                System.out.println("Database is empty! ! ");
            }
        }
        checkAccount();
    }

    public void register() throws IOException {
        boolean flag = true;
        while (flag) {
            System.out.print("Enter your name: ");
            String name = notTakeEmptyString();
            System.out.print("Enter your surname: ");
            String surname = notTakeEmptyString();
            Client client = new Client(name, surname);
            System.out.print("Enter your login: ");
            String login = notTakeEmptyString();
            System.out.print("Enter your password: ");
            String password = notTakeEmptyString();
            Account newAccount = new Account(client.getName(), client.getSurname(), login, password);
            if (!Database.DATABASE.accountsDB.contains(newAccount)) {
                Database.DATABASE.accountsDB.add(newAccount);
                System.out.println("You created a new account. ");
                flag = false;
            } else {
                System.out.println("Such account has already exist! ");
            }
        }
    }

    private void checkAccount() throws IOException {
        while (true) {
            System.out.print("Do you want to check your account? Y/N ");
            String answer = notTakeEmptyString();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                Client.showAllRentedCarsByClient();
                break;
            } else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("Enter the correct character");
            }
        }
    }

    public void rentACar() throws IOException {
        if (loginOrRegister()){
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
                System.out.print("\nWhich car you want to return? Enter the car ID number from below list: ");
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
