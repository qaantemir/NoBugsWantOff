package java_express.oop.farm;

public class Cow extends FarmAnimal {
    @Override
    public void function() {
        System.out.println("Корова дает молоко");
    }

    @Override
    public void feed() {
        System.out.println("Корова употребляет сено");
    }

    @Override
    public void care() {
        System.out.println("Корова нуждается в выпасе");
    }
}
