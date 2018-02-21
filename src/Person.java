public class Person {
    private String name;
    private String surname;

    Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Person() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
