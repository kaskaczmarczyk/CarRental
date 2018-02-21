public class Client extends Person{

    private String login;
    private String password;
    public static Client client;

    public Client() {}

    Client(String name, String surname, String login, String password) {
        super(name, surname);
        this.login = login;
        this.password = password;
    }

    public Client getClient() {
        return this;
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
