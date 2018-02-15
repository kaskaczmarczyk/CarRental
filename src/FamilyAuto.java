public class FamilyAuto extends Auto {
    private int fotelikDzieciecy;

    public FamilyAuto(String brand, double engineCapacity, int fotelikDzieciecy) {
        super(brand, engineCapacity);
        this.fotelikDzieciecy = fotelikDzieciecy;
    }

    public FamilyAuto() {}

    public String toString() {
        return (super.toString() + "\n\tfotelik dziecięcy: " + fotelikDzieciecy);
    }
}
