public class Auto {

    private String brand;
    private double engineCapacity;
    private String fuel;
    private int km;
    private int numberOfSeats;
    private boolean airConditioning;

    public Auto(String brand, double engineCapacity, String fuel, int km, int numberOfSeats, boolean airConditioning) {
        this.brand = brand;
        this.engineCapacity = engineCapacity;
        this.fuel = fuel;
        this.km = km;
        this.numberOfSeats = numberOfSeats;
        this.airConditioning = airConditioning;
    }

    public String toString(){
        return ("Auto: " + "\n\tbrand: " + brand + "\n\tengine capacity: " + engineCapacity + "\n\tfuel: " + fuel + "\n\tnumber of horsepower: " + km
        + "\n\tnumber of seats: " + numberOfSeats + "\n\tair conditioning: " + airConditioning);
    }
}
