package java_express.oop.menu;

public class HotDish extends Dish {
    private int temperature;

    public HotDish(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public void getDescription() {
        System.out.println("Температура: " + this.temperature);
    }

    @Override
    public String toString() {
        return "HotDish{" +
                "temperature=" + temperature +
                '}';
    }
}
