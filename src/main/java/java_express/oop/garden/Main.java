package java_express.oop.garden;

public class Main {
    public static void main(String[] args) {
        Garden garden = new Garden();

        garden.add(new Cactus());
        garden.showActions();
    }
}
