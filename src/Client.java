public class Client extends Person{

    private String login;
    private String password;
    public static Client client;

    public Client() {}

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
