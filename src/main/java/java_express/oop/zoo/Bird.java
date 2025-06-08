package java_express.oop.zoo;

public class Bird extends Animal {
    @Override
    public void makeSounds() {
        System.out.println("Птица чирикает");
    }

    @Override
    public void move() {
        System.out.println("Птица летает");
    }
}
