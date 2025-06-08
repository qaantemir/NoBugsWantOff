package java_express.oop.pets;

public class Cat extends Pet {
    @Override
    public void doAction() {
        System.out.println("Кошка играет");
    }

    @Override
    public void feed() {
        System.out.println("Кошка ест влажный корм");
    }
}
