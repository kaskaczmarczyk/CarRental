public class SportAuto extends Auto{

    private boolean szyberdach;
    private boolean turbo;

    public SportAuto (String brand, double engineCapacity, boolean szyberdach, boolean turbo) {
        super (brand, engineCapacity);
        this.szyberdach= szyberdach;
        this.turbo = turbo;
    }

    public String toString() {
        return (super.toString() + " \n\tszyberdach : " + szyberdach + "\n\tturbo: " + turbo);
    }

}
