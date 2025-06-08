package java_express.oop.aqua;

public class Aquarium {
    private SeaAnimal seaAnimal;

    public void add(SeaAnimal seaAnimal) {
        this.seaAnimal = seaAnimal;
    }

    public void showActions() {
        seaAnimal.move();
    }
}
