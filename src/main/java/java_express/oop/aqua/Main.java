package java_express.oop.aqua;

public class Main {
    public static void main(String[] args) {
        Aquarium aquarium = new Aquarium();

        aquarium.add(new Shark());
        aquarium.showActions();
    }
}
