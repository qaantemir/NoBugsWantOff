package java_express.oop.farm;

public class Chicken extends FarmAnimal {
    @Override
    public void function() {
        System.out.println("Курица дает яйца");
    }

    @Override
    public void feed() {
        System.out.println("Курица клюет зерно");
    }

    @Override
    public void care() {
        System.out.println("Курица нуждается в кормушке");
    }
}
