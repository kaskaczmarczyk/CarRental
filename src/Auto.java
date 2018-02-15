public class Auto {

    private String brand;
    private double engineCapacity;

    public Auto() {}

    public Auto(String brand, double engineCapacity)
    {
        this.brand = brand;
        this.engineCapacity = engineCapacity;
    }

    public String toString(){
        return ("Auto: " + "\n\tbrand: " + brand + "\n\tengine capacity: " + engineCapacity);
    }
}
