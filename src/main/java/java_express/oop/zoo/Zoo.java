package java_express.oop.zoo;

public class Zoo {
    Animal animal;

    public void add(Animal animal) {
        this.animal = animal;
    }

    public void showActions() {
        animal.makeSounds();
        animal.move();
    }
}
