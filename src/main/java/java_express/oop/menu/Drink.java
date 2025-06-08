package java_express.oop.menu;

public class Drink extends Dish {
    private int volume;

    public Drink(int volume) {
        this.volume = volume;
    }


    @Override
    public void getDescription() {
        System.out.println("volume: " + this.volume);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "volume=" + volume +
                '}';
    }
}
