public class Auto {

    private String brand;
    private String model;
    private double engineCapacity;
    private String fuel;
    private int km;
    private int numberOfSeats;
    private boolean airConditioning;
    private double pricePerDay;
    private boolean isAvailable;

    Auto() {}

    public Auto(String brand, String model, double engineCapacity, String fuel, int km, int numberOfSeats,
                double pricePerDay, boolean airConditioning, boolean isAvailable) {
        this.brand = brand;
        this.model = model;
        this.engineCapacity = engineCapacity;
        this.fuel = fuel;
        this.km = km;
        this.numberOfSeats = numberOfSeats;
        this.pricePerDay = pricePerDay;
        this.airConditioning = airConditioning;
        this.isAvailable = isAvailable;
    }

    public String toString(){
        return ("Auto: " + "\n\tbrand: " + brand + "\n\tengine capacity: " + engineCapacity + "\n\tfuel: " + fuel + "\n\tnumber of horsepower: " + km
        + "\n\tnumber of seats: " + numberOfSeats + "\n\tair conditioning: " + airConditioning);
    }
}
