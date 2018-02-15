import java.util.HashMap;

public class Account extends Client{

    private String login;
    private String password;

    static HashMap<String, String> accounts = new HashMap<>();

    Account(String name, String surname, String login, String password) {
        super(name, surname);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
