import java.io.IOException;

public class ManageAccounts {

    private CheckInput checkInput = new CheckInput();
    Account account;

    public boolean loginOrRegister() throws IOException {
        while (true) {
            System.out.print("Do you have an account in our car rental? Y/N ");
            String answer = checkInput.notTakeEmptyString();
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
                String login = checkInput.notTakeEmptyString();
                System.out.print("Enter your password: ");
                String password = checkInput.notTakeEmptyString();
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
            String name = checkInput.notTakeEmptyString();
            System.out.print("Enter your surname: ");
            String surname = checkInput.notTakeEmptyString();
            Client.client = new Client(name, surname);
            System.out.print("Enter your login: ");
            String login = checkInput.notTakeEmptyString();
            System.out.print("Enter your password: ");
            String password = checkInput.notTakeEmptyString();
            Account newAccount = new Account(Client.client.getName(), Client.client.getSurname(), login, password);
            if (!Database.DATABASE.accountsDB.contains(newAccount)) {
                Database.DATABASE.accountsDB.add(newAccount);
                Client.client.addClient(newAccount.getClient());
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
            String answer = checkInput.notTakeEmptyString();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                Client.client.showAllRentedCarsByClient();
                break;
            } else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("Enter the correct character");
            }
        }
    }
}
